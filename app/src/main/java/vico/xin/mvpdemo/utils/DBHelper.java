package vico.xin.mvpdemo.utils;

import android.content.Context;

import vico.xin.mvpdemo.dto.Contact2;
import vico.xin.mvpdemo.dto.Contact2Dao;
import vico.xin.mvpdemo.dto.DaoMaster;
import vico.xin.mvpdemo.dto.DaoSession;
import vico.xin.mvpdemo.dto.StudentDao;

/**
 * Created by wangc on 2017/12/7
 * E-MAIL:274281610@QQ.COM
 *
 */

public class DBHelper {
    private static DBHelper instance;
    private static Context mContext;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

//    private StudentDao studentDao;
    private Contact2Dao contact2Dao;

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    "im-db.db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    private DBHelper() {
    }

    public static void init(Context context) {
        mContext = context;
        instance = new DBHelper();
        // 数据库对象
        DaoSession daoSession = getDaoSession(mContext);
//        instance.setStudentDao(daoSession.getStudentDao());
        instance.setContact2Dao(daoSession.getContact2Dao());

    }



    public static DBHelper getInstance() {
        return instance;
    }

//    public StudentDao getStudentDao() {
//        return studentDao;
//    }
//
//    public void setStudentDao(StudentDao studentDao) {
//        this.studentDao = studentDao;
//    }

    public Contact2Dao getContact2Dao(){
        return contact2Dao;
    }

    public void setContact2Dao (Contact2Dao contact2Dao){
        this.contact2Dao = contact2Dao;
    }

}
