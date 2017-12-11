package vico.xin.mvpdemo.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vico.xin.mvpdemo.dto.Contact2;
import vico.xin.mvpdemo.dto.Contact2Dao;
import vico.xin.mvpdemo.utils.DBHelper;

/**
 * 获取通讯录中修改或者增加的联系人信息，将修改的部分传给后台
 * <p>
 * Created by wangc on 2017/12/8
 * E-MAIL:274281610@QQ.COM
 */


public class ContactIntentService extends IntentService {
    //原始的
    private List<Contact2> oldContactLists;
    //修改的
    private List<Contact2> newContactLists;

    public ContactIntentService() {
        super("contactIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.init(this);
        oldContactLists = new ArrayList<>();
        newContactLists = new ArrayList<>();
        initHashMap();
        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, contactObserver);

    }

    /**
     * 监听通讯录变化
     */
    private ContentObserver contactObserver = new ContentObserver(new Handler()) {
        @Override

        public boolean deliverSelfNotifications() {

            return super.deliverSelfNotifications();

        }

        @Override

        public void onChange(boolean selfChange) {

            super.onChange(selfChange);
            boolean needUpdate = isContactChanged();
            if (needUpdate) {
                Log.e("+++", "通讯录被修改了:" + newContactLists.size());

                for (int i = 0; i < newContactLists.size(); i++) {
                    Log.e("+++", "姓名：" + newContactLists.get(i).name + "  " + "号码：" + newContactLists.get(i).phone + "   version:" + newContactLists.get(i).vesion);
                }
                upLoad(newContactLists);

                getContentResolver().unregisterContentObserver(contactObserver);
            }

        }
    };

    /**
     * 记录下RawContacts._ID和对应的version
     */
    public void initHashMap() {
        //如果本地数据库没有数据，上传全部通讯录
        if (DBHelper.getInstance().getContact2Dao().queryBuilder().list() == null
                || DBHelper.getInstance().getContact2Dao().queryBuilder().list().size() <= 0) {

            ContentResolver _contentResolver = getContentResolver();
            Cursor cursor = _contentResolver.query(
                    ContactsContract.RawContacts.CONTENT_URI, null, null, null,
                    null);

            //清空本地数据库
            oldContactLists.clear();
            DBHelper.getInstance().getContact2Dao().deleteAll();

            while (cursor.moveToNext()) {
                Long contactID = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.RawContacts._ID));

                long contactVersion = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.RawContacts.VERSION));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Contact2 contact2 = new Contact2();
                contact2.vesion = contactVersion;
                contact2.name = name;
                contact2.contactID = contactID;
                oldContactLists.add(contact2);
            }
            cursor.close();

            //去掉重复,因为上面在遍历的时候会循环添加3次
            for (int i = 0; i < oldContactLists.size(); i++) {
                for (int j = oldContactLists.size() - 1; j > i; j--) {
                    if (oldContactLists.get(j).name.equals(oldContactLists.get(i).name)) {
                        oldContactLists.remove(j);
                    }
                }
            }

            Log.e("+++", "原始通讯录人数：" + oldContactLists.size());


            upLoad(oldContactLists);
        } else {
            //本地数据库有数据和本地数据库比较后上传修改和增加的联系人信息
            oldContactLists.addAll(DBHelper.getInstance().getContact2Dao().queryBuilder().list());

        }
    }

    /**
     * 上传通讯录
     *
     * @param removeDuplicateContactLists
     */
    private void upLoad(List<Contact2> removeDuplicateContactLists) {
        Log.e("+++", "上传完整，修改数据库");

        Contact2Dao contact2Dao = DBHelper.getInstance().getContact2Dao();
        for (int i = 0; i < removeDuplicateContactLists.size(); i++) {
            contact2Dao.insert(removeDuplicateContactLists.get(i));
        }

        Log.e("+++", "数据库原始通讯录人数：" + DBHelper.getInstance().getContact2Dao().queryBuilder().list().size());
    }

    /**
     * 判断是否有改变
     *
     * @return
     */
    public boolean isContactChanged() {
        boolean theReturn = false;
        ContentResolver _contentResolver = getContentResolver();
        Cursor cursor = _contentResolver.query(
                ContactsContract.RawContacts.CONTENT_URI, null, null, null,
                null);

        String phoneNumber = null;
        newContactLists.clear();
        //当前获取到的最新通讯录
        List<Contact2> tempLists = new ArrayList<>();

        while (cursor.moveToNext()) {
            Long contactID = cursor.getLong(cursor
                    .getColumnIndex(ContactsContract.RawContacts._ID));
            long contactVersion = cursor.getLong(cursor
                    .getColumnIndex(ContactsContract.RawContacts.VERSION));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Contact2 contact2 = new Contact2();
            contact2.vesion = contactVersion;
            contact2.name = name;
            contact2.contactID = contactID;
            tempLists.add(contact2);
        }
        cursor.close();

        //
        for (int i = 0; i < tempLists.size(); i++) {
            for (int j = tempLists.size() - 1; j > i; j--) {
                if (tempLists.get(j).name.equals(tempLists.get(i).name)) {
                    tempLists.remove(j);
                }
            }
        }


        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < oldContactLists.size(); i++) {
            ids.add(oldContactLists.get(i).contactID);
        }

        for (int i = 0; i < tempLists.size(); i++) {
            //联系人之前存在
            Long contactID = tempLists.get(i).contactID;
            int index = ids.indexOf(contactID);
            if (ids.contains(contactID)) {
                //老的version
                long version = oldContactLists.get(index).vesion;
                //version和之前保存的不一致，联系人被修改
                if (version != tempLists.get(i).vesion) {
                    phoneNumber = getPhoneNumber(contactID);
                    Contact2 contact2 = new Contact2();
                    contact2.vesion = tempLists.get(i).vesion;
                    contact2.name = tempLists.get(i).name;
                    contact2.phone = phoneNumber;
                    contact2.contactID = tempLists.get(i).contactID;
                    newContactLists.add(contact2);
                    theReturn = true;
                }
            } else {
                //联系人不存在，新增
                phoneNumber = getPhoneNumber(contactID);
                Contact2 contact2 = new Contact2();
                contact2.vesion = tempLists.get(i).vesion;
                contact2.name = tempLists.get(i).name;
                contact2.phone = phoneNumber;
                contact2.contactID = tempLists.get(i).contactID;
                newContactLists.add(contact2);
                theReturn = true;
            }
        }

        return theReturn;
    }

    /**
     * 根据contactID得到联系人号码
     *
     * @param contactID
     * @return
     */
    private String getPhoneNumber(Long contactID) {
        /**
         * The best way to read a raw contact along with all the data associated with it is by using the ContactsContract.RawContacts.Entity directory. If the raw contact has data rows, the Entity cursor will contain a row for each data row. If the raw contact has no data rows, the cursor will still contain one row with the raw contact-level information.
         */
        //下面这段代码从google官网copy过来的
        Uri rawContactUri = ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, contactID);
        Uri entityUri = Uri.withAppendedPath(rawContactUri, ContactsContract.RawContacts.Entity.CONTENT_DIRECTORY);
        Cursor c = getContentResolver().query(entityUri,
                new String[]{ContactsContract.RawContacts.SOURCE_ID, ContactsContract.Contacts.Entity.DATA_ID, ContactsContract.RawContacts.Entity.MIMETYPE, ContactsContract.Contacts.Entity.DATA1},
                null, null, null);
        try {
            while (c.moveToNext()) {
                String sourceId = c.getString(0);
                if (!c.isNull(1)) {
                    String mimeType = c.getString(2);
                    String data = c.getString(3);
                    return data;
                }
            }
        } finally {
            c.close();
        }
        return "";
    }

}
