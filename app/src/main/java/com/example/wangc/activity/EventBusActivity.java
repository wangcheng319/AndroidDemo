package com.example.wangc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Wangc on 2016/5/17
 * E-MAIL:274281610@QQ.COM
 * <p/>
 * 用于熟悉EventBus的基本使用
 */
public class EventBusActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        textView = (TextView) findViewById(R.id.event_bus_tv);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_to_view:
                EventBus.getDefault().post(new MsgEvent("this is for view"));
                break;
            case R.id.send_message_to_fragment:
                EventBus.getDefault().post(new MsgEvent_Fragmeng("this is for fragment"));
                break;
        }

    }

    @Subscribe
    public void onMessageEvent(MsgEvent msgEvent) {
        textView.setText(msgEvent.message);
    }


    /**
     * 传值给当前activity的view
     */
    public class MsgEvent {
        public final String message;

        public MsgEvent(String message) {
            this.message = message;
        }
    }

    /**
     * 传值给当前activity的fragment
     */
    public class MsgEvent_Fragmeng {
        public final String message;

        public MsgEvent_Fragmeng(String message) {
            this.message = message;
        }
    }
}
