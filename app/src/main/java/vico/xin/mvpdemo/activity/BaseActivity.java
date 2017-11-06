package vico.xin.mvpdemo.activity;

import android.app.ProgressDialog;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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

/**
 * Created by wangc on 2017/5/18
 * E-MAIL:274281610@QQ.COM
 */

public class BaseActivity extends AppCompatActivity {
    public  ProgressDialog progressDialog;

    public TextView none;
    public TextView error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
            //this is from branch 1
        findViewById(R.id.btn_to_module_java).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is from master
            }
        });

    }
}
