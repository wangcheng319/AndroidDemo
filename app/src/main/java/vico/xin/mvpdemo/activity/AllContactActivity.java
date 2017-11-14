package vico.xin.mvpdemo.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import vico.xin.mvpdemo.R;
import vico.xin.mvpdemo.fragment.FragmentFour;
import vico.xin.mvpdemo.view.SunView;

/**
 * Created by wangc on 2017/5/18
 * E-MAIL:274281610@QQ.COM
 *
 * 直接调用系统联系人界面  获取所有联系人（含SIM卡中的）
 *
 */

public class AllContactActivity extends AppCompatActivity {
    public ProgressDialog progressDialog;

    public TextView textView;
    public TextView error;
    SunView sunView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        textView = (TextView) findViewById(R.id.tv);

        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //  String num = data.getStringExtra("num");
            Uri uri = data.getData();
            String num = null;
            String name = null;
            // 创建内容解析者
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri,
                    null, null, null, null);
            if (cursor.moveToNext()) {
                num = cursor.getString(cursor.getColumnIndex("data1"));
                name = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));

            }else{
                Toast.makeText(this,"请开启通讯录权限",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
            cursor.close();
            //  把电话号码中的  -  符号 替换成空格
            //  给 EditText空间设置你选择的联系号码
            textView.setText(name + num);


        }

    }


    private void GetSimContact(String add) {
        //读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(add));
            Uri uri = intent.getData();
            Cursor mCursor = getContentResolver().query(uri, null, null, null, null);
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    // 取得联系人名字
                    int nameFieldColumnIndex = mCursor.getColumnIndex("name");
                    String contactName = mCursor.getString(nameFieldColumnIndex);
                    // 取得电话号码
                    int numberFieldColumnIndex = mCursor
                            .getColumnIndex("number");
                    String userNumber = mCursor.getString(numberFieldColumnIndex);

                }
                mCursor.close();
            }
        } catch (Exception e) {
            Log.i("eoe", e.toString());
        }
    }
}
