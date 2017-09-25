package vico.xin.mvpdemo.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_login.*
import vico.xin.mvpdemo.R
import vico.xin.mvpdemo.dto.LoginDto
import vico.xin.mvpdemo.dto.UserAllInfo
import vico.xin.mvpdemo.presenter.LoginContract
import vico.xin.mvpdemo.presenter.LoginPresenter
import java.util.concurrent.TimeUnit

/**
 * 登录页面,使用kotlin实现
 */
class LoginActivity : BaseActivity(), LoginContract.View,View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn->login()
        }
    }

    var presenter: LoginContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        presenter = LoginPresenter(this)

//        btn.setOnClickListener {
//            login()
//        }

        //rxbinding ,button防抖处理，一秒只响应一次
        RxView.clicks(btn)
              .throttleFirst(1,TimeUnit.MILLISECONDS)
              .subscribe( { login() })

    }

    private fun login() {
        val login = LoginDto()
        login.deviceModel = android.os.Build.MODEL
        login.deviceToken = ""
        login.isEm = false
        login.latitude = ""
        login.longitude = ""
        login.networkType = "4G"
        login.location = "上海"
        login.systemVersion = "Android-" + android.os.Build.VERSION.RELEASE
        login.password = "111111"
        login.phone = "15659926163"

        presenter!!.doLogin(login)

    }


    override fun onPause() {
        super.onPause()
        this.presenter!!.unsubscribe()
    }

    override fun showProgressDialog(isShow: Boolean) {
        if (isShow) {
            progressDialog.show()
        } else {
            if (progressDialog.isShowing && !isFinishing) {
                progressDialog.dismiss()
            }
        }
    }

    override fun onSuccess(userAllInfo: UserAllInfo) {
        text.text = "登录结果：" + userAllInfo.phone + "====" + userAllInfo.email

    }

    override fun onError(s: String) {
        text.text = "登录失败：" + s
        text.setTextColor(Color.RED)

    }

}
