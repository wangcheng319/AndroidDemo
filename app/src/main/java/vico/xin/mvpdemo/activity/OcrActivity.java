package vico.xin.mvpdemo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vico.xin.mvpdemo.R;
import vico.xin.mvpdemo.utils.Credentials;
import vico.xin.mvpdemo.utils.Sign;

public class OcrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        String url = "http://recognition.image.myqcloud.com/ocr/handwriting";
        ocr(url,"");


    }

    private void ocr(String url, String json) {
        File file = new File("test.png");
        File image  = drawableToFile(this,R.drawable.ocr,file);
        String appid = "1254148478"; // 用户ocrKey
        String secretid = "AKIDRdidO0Ajtz47ABwxkStLl8SpFg2Jbcjn";
        String secretkey = "AJMKF4nRxPl5MYefcUGCB5tbGqLm8Qcj";
        String auth = null;
        com.qcloud.image.sign.Credentials credentials = new com.qcloud.image.sign.Credentials(appid,secretid,secretkey);
        try {
             auth = Sign.appSign(credentials,"orc-test",0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), image);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "test.jpg", fileBody)
                .addFormDataPart("appid", appid)
                .build();
        Request request = new Request.Builder()
                .addHeader("host","recognition.image.myqcloud.com")
                .addHeader("content-length","")
                .addHeader("content-type","multipart/form-data")
                .addHeader("authorization",auth)
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("+++","失败："+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("+++","成功："+response.toString());
            }
        });
    }

    public File drawableToFile(Context mContext, int drawableId, File fileName){
//        InputStream is = view.getContext().getResources().openRawResource(R.drawable.logo);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), drawableId);
//        Bitmap bitmap = BitmapFactory.decodeStream(is);

        String defaultPath = mContext.getFilesDir()
                .getAbsolutePath() + "/defaultGoodInfo";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            return null;
        }
        String defaultImgPath = defaultPath + "/"+fileName;
        file = new File(defaultImgPath);
        try {
            file.createNewFile();

            FileOutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
//            is.close();
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
