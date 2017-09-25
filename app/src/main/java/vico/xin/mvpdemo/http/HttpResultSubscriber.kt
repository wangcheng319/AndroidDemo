package vico.xin.mvpdemo.http

import android.view.View

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import vico.xin.mvpdemo.dto.BaseDto
import vico.xin.mvpdemo.presenter.BaseView

/**
 * Created by wangc on 2017/5/18
 * E-MAIL:274281610@QQ.COM
 */

abstract class HttpResultSubscriber<T>(var view: BaseView<*>) : Observer<BaseDto<T>> {

    override fun onSubscribe(@NonNull d: Disposable) {

    }

    override fun onNext(@NonNull t: BaseDto<T>) {
        //隐藏进度条
        this.view.showProgressDialog(false)

        if (t.code == 10000) {
            onSuccess(t.response)
        } else if (t.code == 10003) {
            _onError(Throwable("error=" + t.info))
        } else if (t.code == 10401) {
            _onError(Throwable("error=" + t.info))
        } else if (t.code == 10403) {
            _onError(Throwable("error=" + t.info))
        }
    }

    override fun onError(@NonNull e: Throwable) {
        //隐藏进度条
        this.view.showProgressDialog(false)

        _onError(e)
    }

    override fun onComplete() {

    }


    abstract fun onSuccess(t: T)

    abstract fun _onError(e: Throwable)
}
