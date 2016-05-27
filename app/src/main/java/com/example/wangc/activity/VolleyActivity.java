package com.example.wangc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button send_request;
    public String url = "http://janhuu.imwork.net:30319/qianyuApp/requestservices.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        initView();
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        //post请求
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("", "response -> " + response);
                        send_request.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("CmdId", "queryHotRecord");
                map.put("Goal", "record");
                map.put("c_pageCount", "10");
                map.put("c_currentPage", "1");
                map.put("Version", "01");
                return map;
            }
        };


        requestQueue.add(stringRequest);

    }

    private void initView() {
        send_request = (Button) findViewById(R.id.send_volley_request);
    }

    @Override
    public void onClick(View v) {


    }
}
