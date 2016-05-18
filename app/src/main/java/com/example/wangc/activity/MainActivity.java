package com.example.wangc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/**
 * Created by Wangc on 2016/5/17
 * E-MAIL:274281610@QQ.COM
 *
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_view:
                startActivity(new Intent(MainActivity.this,ViewActivity.class));
                break;
            case R.id.btn_view_group:
                startActivity(new Intent(MainActivity.this,ViewGroupActivity.class));
                break;
            case R.id.btn_animation:
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
                break;
            case R.id.btn_rx_android:
                startActivity(new Intent(MainActivity.this,RxAndroidActivity.class));
                break;
            case R.id.btn_event_bus:
                startActivity(new Intent(MainActivity.this,RetrofitActivity.class));
                break;
            case R.id.btn_ok_http:
                startActivity(new Intent(MainActivity.this,OkhttpActivity.class));
                break;
            case R.id.btn_retrofit:
                startActivity(new Intent(MainActivity.this,RetrofitActivity.class));
                break;
            case R.id.btn_view_drag_helper:

                break;
            case R.id.btn_view_hot_fix:
                break;

        }
    }
}
