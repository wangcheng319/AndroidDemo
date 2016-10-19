package com.example.wangc.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

         mWebView = (WebView) findViewById(R.id.wb);
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);

        mWebView.loadUrl("file:///android_asset/test.html");

        //在js中调用本地java方法
        mWebView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

        //添加客户端支持
        mWebView.setWebChromeClient(new WebChromeClient());

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfoToJs();
            }
        });

    }


    //在java中调用js代码
    public void sendInfoToJs() {
        String msg = ((EditText) findViewById(R.id.input_et)).getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
        mWebView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
    }

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }
}
