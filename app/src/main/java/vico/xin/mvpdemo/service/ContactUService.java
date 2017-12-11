package vico.xin.mvpdemo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;

import java.util.ArrayList;

import vico.xin.mvpdemo.dto.Contact2;
import vico.xin.mvpdemo.dto.ContactU;
import vico.xin.mvpdemo.utils.ContactUtil;


/**
 * 通讯录上传
 *
 */
public class ContactUService extends Service {
	
	private String mUid;

	public static Intent getIntent(Context context, String uid){
		Intent intent = new Intent(context,ContactUService.class);
		intent.putExtra("uid", uid);
		return intent;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
    public void onCreate() {
        super.onCreate();
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(intent==null){
			stopSelf();
		}
		
		mUid = intent.getStringExtra("uid");
		
		new Thread(new Runnable() {
			 @Override
	            public void run() {
				 try {
					 ContactUtil cu = new ContactUtil();
					 ArrayList<Contact2> list  = cu.getAllContacts(ContactUService.this);
					 
					 ContactU c = new ContactU();
					 c.cid = mUid;
//					 c.deviceToken = Utils.getUniquePsuedoID();
					 c.contacts = list;
					 
					 if(list!=null && list.size()>0){
						 Gson gson = new Gson();
						 String json = gson.toJson(c);
						 upload(json);
					 }else{
						 stopSelf();
					 }
				} catch (Exception e) {
					stopSelf();
				}
			 }
		}).start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
    }

	private void upload(String json){
//		ApiExecutor.getUpLoadInstance().upLoadContact(RRCrypto.encryptAES(json, MyApplication.SECRETKEY))
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new Subscriber<BaseDto<String>>() {
//					@Override
//					public void onCompleted() {
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						stopSelf();
//					}
//
//					@Override
//					public void onNext(BaseDto<String> stringBaseDto) {
//						stopSelf();
//					}
//
//					@Override
//					public void onStart() {
//						super.onStart();
//					}
//				});
	}
}
