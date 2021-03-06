package vico.xin.mvpdemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import store.tyb.vico.xin.remotapp.AIDLRemoteService;
import vico.xin.mvpdemo.IMyAidlInterface;
import vico.xin.mvpdemo.R;

public class AidlActivity extends AppCompatActivity {


    private IMyAidlInterface mMyAIDL;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("+++", "onServiceConnected");
            mMyAIDL = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("+++", "onServiceDisconnected");
            mMyAIDL = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Button startService = (Button) findViewById(R.id.btn);
        Button bindService = (Button) findViewById(R.id.btn1);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("+++",mMyAIDL.test("我是app来的"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AidlActivity.this, AIDLRemoteService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
