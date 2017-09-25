package vico.xin.mvpdemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import vico.xin.mvpdemo.R;

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

        progressDialog = new ProgressDialog(this);
        none = (TextView) findViewById(R.id.tv_none);
        error = (TextView) findViewById(R.id.tv_error);
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                e.onNext("hello");

            }
        }, BackpressureStrategy.BUFFER).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                });
    }
}
