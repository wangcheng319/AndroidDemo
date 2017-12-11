package vico.xin.mvpdemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;


import java.util.ArrayList;

import vico.xin.mvpdemo.dto.Contact2;


public class ContactUtil {
	Context mContext = null;

	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID,
			Phone.LAST_TIME_CONTACTED, Phone.TIMES_CONTACTED };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** 最后联系时间 **/
	private static final int PHONES_LAST_TIME_CONTACTED = 4;

	/** 联系次数 **/
	private static final int PHONES_TIMES_CONTACTED = 5;

	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** 联系人头像 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	private ArrayList<Contact2> mContactsList = new ArrayList<Contact2>();
	
	public ArrayList<Contact2> getAllContacts(Context cxt){
		mContactsList.clear();
		
		mContext = cxt;
		getPhoneContacts();
//		getSIMContacts();
		
		return mContactsList;
	}
	
	/** 得到手机通讯录联系人信息 **/
	private void getPhoneContacts() {
		ContentResolver resolver = mContext.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX).trim().replaceAll(" ", "");
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				
//				System.out.println(contactName);
//				System.out.println("时间:"+phoneCursor.getLong(PHONES_LAST_TIME_CONTACTED));
//				System.out.println("次数:"+phoneCursor.getLong(PHONES_TIMES_CONTACTED));

				Contact2 contact = new Contact2();
				contact.name = contactName;
				contact.phone = phoneNumber;
				contact.callTimes = phoneCursor.getLong(PHONES_TIMES_CONTACTED);
				contact.lastCallTimeMillis = phoneCursor.getLong(PHONES_LAST_TIME_CONTACTED);
				contact.contactID = contactid;

//				mContactsName.add(contactName);
//				mContactsNumber.add(phoneNumber);
				
				mContactsList.add(contact);
			}

			phoneCursor.close();
		}
	}

	/** 得到手机SIM卡联系人人信息 **/
	private void getSIMContacts() {
		ContentResolver resolver = mContext.getContentResolver();
		// 获取Sims卡联系人
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX).trim().replaceAll(" ", "");
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				// Sim卡中没有联系人头像

				Contact2 contact = new Contact2();
				contact.name = contactName;
				contact.phone = phoneNumber;
				contact.callTimes = phoneCursor.getLong(PHONES_TIMES_CONTACTED);
				contact.lastCallTimeMillis = phoneCursor.getLong(PHONES_LAST_TIME_CONTACTED);
				
//				mContactsName.add(contactName);
//				mContactsNumber.add(phoneNumber);
				
				mContactsList.add(contact);
			}

			phoneCursor.close();
		}
	}
	
}
