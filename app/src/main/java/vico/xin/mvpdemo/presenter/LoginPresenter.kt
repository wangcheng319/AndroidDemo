package vico.xin.mvpdemo.presenter

import android.widget.Toast
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import vico.xin.mvpdemo.crypto.RRCrypto
import vico.xin.mvpdemo.dto.LoginDto
import vico.xin.mvpdemo.dto.UserAllInfo
import vico.xin.mvpdemo.http.ApiExecutor
import vico.xin.mvpdemo.http.HttpResultSubscriber

/**
 * Created by wangc on 2017/5/18
 * E-MAIL:274281610@QQ.COM
 */

class LoginPresenter(private val loginView: LoginContract.View) : LoginContract.Presenter {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun subscribe(o: Any) {
        /*处理业务逻辑，网络请求*/
        doLogin(o as LoginDto)
    }

    /*解除订阅关系，避免内存泄漏*/
    override fun unsubscribe() {

        compositeDisposable.clear()
    }

    override fun doLogin(loginDto: LoginDto) {

        val gson = Gson()
        val json = gson.toJson(loginDto)

        //显示进度条
        loginView.showProgressDialog(true)

//        ApiExecutor.getInstance()
//                .Login(RRCrypto.encryptAES(json, "fsgjernfjwnfjeft"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : HttpResultSubscriber<UserAllInfo>(loginView) {
//                    override fun onSubscribe(@NonNull d: Disposable) {
//                        //添加
//                        compositeDisposable.add(d)
//                    }
//
//                    override fun onSuccess(userAllInfo: UserAllInfo) {
//                        loginView.onSuccess(userAllInfo)
//                    }
//
//                    override fun _onError(e: Throwable) {
//                        loginView.onError(e.toString())
//
//                    }
//                })
    }
}

