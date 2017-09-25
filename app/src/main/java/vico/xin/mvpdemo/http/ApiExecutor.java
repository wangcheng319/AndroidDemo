package vico.xin.mvpdemo.http;

/**
 * autour: wangc
 * date: 2016/12/6 11:40 
*/
public class ApiExecutor {


    //测试环境
   public static String BASE_URL = "http://cl.api.90dai.com";
   public static String BASE_UPLOAD_URL = "http://cl.upload.90dai.com";


    private static class SingletonHolder {
        private static final ApiService INSTANCE = ApiService.Factory.create();
    }

    private static class SingletonHolder1 {
        private static final ApiService INSTANCE = ApiService.Factory.createUpload();
    }

    private static class SingletonHolder2 {
        private static final ApiService INSTANCE = ApiService.Factory.createUnGson();
    }

    public static final ApiService getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public static final ApiService getUpLoadInstance() {
        return SingletonHolder1.INSTANCE;
    }

    public static final ApiService getUnGsonInstance() {
        return SingletonHolder2.INSTANCE;
    }

}
