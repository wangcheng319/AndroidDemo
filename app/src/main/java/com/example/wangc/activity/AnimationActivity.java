package com.example.wangc.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Wangc on 2016/5/17
 * E-MAIL:274281610@QQ.COM
 */
public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        final ImageView mimageView = (ImageView) findViewById(R.id.iv_anim);
        Button button = (Button) findViewById(R.id.btn_anim_top);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mimageView, "rotationX", 0, 180).setDuration(500);
                objectAnimator.start();
            }
        });
    }
}
