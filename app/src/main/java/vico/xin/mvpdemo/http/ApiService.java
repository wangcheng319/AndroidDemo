package vico.xin.mvpdemo.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vico.xin.mvpdemo.BuildConfig;
import vico.xin.mvpdemo.dto.BaseDto;
import vico.xin.mvpdemo.dto.UserAllInfo;

/** 
 * autour: wangc
 * date: 2016/12/6 11:40
*/
public interface ApiService {



    /*登录*/
    @FormUrlEncoded
    @POST("/client/custom/login")
    Observable<BaseDto<UserAllInfo>> Login(@Field("json") String json);
    //可以获取到响应头信息
//    Observable<retrofit2.Response<BaseDto<UserAllInfo>>> Login(@Field("json") String json);




    class Factory {

        private static OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ParamsInterceptor())
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)//超时设置
                //.sslSocketFactory() https通信
                .build();

        //返回gson解析后的结果
        public static ApiService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiExecutor.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            return retrofit.create(ApiService.class);
        }

        //上传
        public static ApiService createUpload() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiExecutor.BASE_UPLOAD_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            return retrofit.create(ApiService.class);
        }
        //直接返回string
        public static ApiService createUnGson() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiExecutor.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            return retrofit.create(ApiService.class);
        }
    }


    class ParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl httpUrl = request.url().newBuilder()
//                    .addQueryParameter("uuid", "484s6d4f65")    //这里比较适合添加所有请求都会传的参数
                    .build();
            request = request.newBuilder()
                    .addHeader("CL-app", "CLAC")
                    .addHeader("CL-app-v", "3.2.0")
                    .addHeader("CL-app-m", "Prod")
                    .addHeader("uuid", "")
                    .addHeader("appKey", "")
                    .addHeader("source", "tyhj")
                    .build();

            request = request.newBuilder().url(httpUrl).build();
            return chain.proceed(request);
        }
    }

    /**
     * 保存返回的cookies
     */
    class ReceivedCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            //这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                final StringBuffer cookieBuffer = new StringBuffer();

                Observable.fromArray(originalResponse.header("Set-Cookie")).map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        cookieBuffer.append(cookie).append(";");
                    }
                });

//                SharedPreferences sharedPreferences = MyApplication.getInstance().getApplicationContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("cookie", cookieBuffer.toString());
//                editor.commit();

            }
            return originalResponse;
        }
    }

    /**
     * 添加cookies
     */
    class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request.Builder builder = chain.request().newBuilder();
//            SharedPreferences sharedPreferences = MyApplication.getInstance().getApplicationContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
//            Observable.just(sharedPreferences.getString("cookie", ""))
//                    .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(String cookie) {
//                            //添加cookie
//                            builder.addHeader("Cookie", cookie);
//                        }
//                    });
            return chain.proceed(builder.build());
        }
    }

    /**
     * 请求打印
     */
    class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (BuildConfig.DEBUG){
                Log.v("http+++", "request:" + request.toString());
            }
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            if (BuildConfig.DEBUG){
                Log.i("http+++", String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            }
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();

            if (BuildConfig.DEBUG){
//                L.setIsLog(true);
//                L.json("http+++result",content);
            }


            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }

}
