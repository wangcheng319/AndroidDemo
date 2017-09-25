package vico.xin.mvpdemo.activity

import android.util.Log

import org.reactivestreams.Subscription

import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

/**
 * Created by wangc on 2017/5/25
 * E-MAIL:274281610@QQ.COM
 */

class t {
    fun test() {
        Flowable.just("1", "2", "3", "4", "5", "6", "7")
                .skip(3)
                .take(2)
                .map { s -> "hello" + s }.subscribe(object : FlowableSubscriber<Any> {
            override fun onSubscribe(@NonNull s: Subscription) {

            }

            override fun onNext(o: Any) {
                Log.e("", o.toString() + "你好")
            }

            override fun onComplete() {

            }

            override fun onError(t: Throwable) {

            }
        })
    }
}
