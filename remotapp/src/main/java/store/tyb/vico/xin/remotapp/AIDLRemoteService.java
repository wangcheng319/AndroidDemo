package store.tyb.vico.xin.remotapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;

import vico.xin.mvpdemo.IMyAidlInterface;

/**
 * Created by wangc on 2017/11/27
 * E-MAIL:274281610@QQ.COM
 */

public class AIDLRemoteService extends Service {

    private static final String TAG = "+++";


    private final IMyAidlInterface.Stub mBinder=new IMyAidlInterface.Stub(){
        @Override
        public String test(String string) throws RemoteException {
            Log.e("+++",string);
            return "我是remoteapp返回的";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
