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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vico.xin.mvpdemo.dto.Contact2;

/**
 * Created by wangc on 2017/12/8
 * E-MAIL:274281610@QQ.COM
 */



public class ContactIntentService extends IntentService {
    //原始的
    private Map<Long,Contact2> hashMap;
    //修改的
    private Map<Long,Contact2> hashMap1;

    public ContactIntentService() {
            super("contactIntentService");
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onHandleIntent(Intent intent) {

        }

        @Override
        public void onCreate() {
            super.onCreate();
            hashMap = new HashMap<>();
            hashMap1 = new HashMap<>();
            initHashMap();
            getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, new contactObserver(new Handler()));

        }

    /**
     * 监听联系人是否改变
     */
    public final class contactObserver extends ContentObserver {

            public contactObserver(Handler handler) {
                super(handler);
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);

                boolean needUpdate = isContactChanged();
                if (needUpdate) {
                    Log.e("+++", "通讯录被修改了");

                    Iterator iter = hashMap1.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Object key = entry.getKey();
                        Contact2 value = (Contact2) entry.getValue();
                        Log.e("+++",key + ":" + "姓名："+value.name+"  "+"号码："+value.phone+"   version:"+value.vesion);

                    }

                }
            }
        }

    /**
     * 记录下RawContacts._ID和对应的version
     */
    public void initHashMap() {
            ContentResolver _contentResolver = getContentResolver();
            Cursor cursor = _contentResolver.query(
                    ContactsContract.RawContacts.CONTENT_URI, null, null, null,
                    null);

            while (cursor.moveToNext()) {
                Long contactID = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.RawContacts._ID));

                long contactVersion = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.RawContacts.VERSION));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Contact2 contact2 = new Contact2();
                contact2.vesion = contactVersion;
                contact2.name = name;
                hashMap.put(contactID, contact2);
            }
            cursor.close();
        }

    /**
     * 判断是否有改变
     * @return
     */
    public boolean isContactChanged(){
            boolean theReturn = false;
            ContentResolver _contentResolver = getContentResolver();
            Cursor cursor = _contentResolver.query(
                    ContactsContract.RawContacts.CONTENT_URI, null, null, null,
                    null);

            String phoneNumber = null;


            while (cursor.moveToNext()) {
                Long contactID = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.RawContacts._ID));
                long contactVersion = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.RawContacts.VERSION));
                //联系人之前存在
                if (hashMap.containsKey(contactID)) {
                    long version = hashMap.get(contactID).vesion;
                    //version和之前保存的不一致，联系人被修改
                    if (version != contactVersion) {
                        phoneNumber = getPhoneNumber(contactID);
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Contact2 contact2 = new Contact2();
                        contact2.vesion = contactVersion;
                        contact2.name = name;
                        contact2.phone = phoneNumber;
                        hashMap1.put(contactID,contact2);
                        theReturn = true;
                    }
                }else {
                    //联系人不存在，新增
                    phoneNumber = getPhoneNumber(contactID);
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Contact2 contact2 = new Contact2();
                    contact2.vesion = contactVersion;
                    contact2.name = name;
                    contact2.phone = phoneNumber;
                    hashMap1.put(contactID,contact2);
                    theReturn = true;
                }
            }
            cursor.close();
            return theReturn;
        }

    /**
     * 得到联系人号码
     * @param contactID
     * @return
     */
    private String getPhoneNumber(Long contactID) {
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
