package vico.xin.mvpdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import vico.xin.mvpdemo.R;

public class ViewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);

        findViewById(R.id.btn_to_react).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ViewTestActivity.this,MyReactActivity.class));
            }
        });
    }
}
