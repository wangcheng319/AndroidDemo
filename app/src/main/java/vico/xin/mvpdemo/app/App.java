package vico.xin.mvpdemo.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.view.SurfaceView;

import org.greenrobot.greendao.database.Database;

import vico.xin.mvpdemo.dto.DaoMaster;
import vico.xin.mvpdemo.dto.DaoSession;

//import com.facebook.react.bridge.ReactApplicationContext;

/**
 * Created by wangc on 2017/5/18
 * E-MAIL:274281610@QQ.COM
 */

public class App extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();



        //配置数据库
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
