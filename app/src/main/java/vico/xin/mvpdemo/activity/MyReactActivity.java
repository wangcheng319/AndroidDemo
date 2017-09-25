//package vico.xin.mvpdemo.activity;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.BuildConfig;
//import android.view.KeyEvent;
//
//import com.facebook.react.common.LifecycleState;
//import com.facebook.react.ReactInstanceManager;
//import com.facebook.react.ReactRootView;
//import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
//import com.facebook.react.shell.MainReactPackage;
//
///**
// * Created by wangc on 2017/7/27
// * E-MAIL:274281610@QQ.COM
// */
//
//public class MyReactActivity extends Activity implements DefaultHardwareBackBtnHandler {
//    private ReactRootView mReactRootView;
//    private ReactInstanceManager mReactInstanceManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mReactRootView = new ReactRootView(this);
//        mReactInstanceManager = ReactInstanceManager.builder()
//                .setApplication(getApplication())
//                .setBundleAssetName("index.android.bundle")
//                .setJSMainModuleName("index.android")
//                .addPackage(new MainReactPackage())
//                .setUseDeveloperSupport(BuildConfig.DEBUG)
//                .setInitialLifecycleState(LifecycleState.RESUMED)
//                .build();
//        mReactRootView.startReactApplication(mReactInstanceManager, "rnmvpdemo", null);
//
//        setContentView(mReactRootView);
//    }
//
//    @Override
//    public void invokeDefaultOnBackPressed() {
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onBackPressed();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
//            mReactInstanceManager.showDevOptionsDialog();
//            return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }
//}
