package vico.xin.mvpdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.FlowableSubscriber
import io.reactivex.annotations.NonNull
import org.reactivestreams.Subscription
import vico.xin.mvpdemo.R
import java.util.*

/**
 * Rxjava操作符熟悉
 */
class RxjavaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)

        /*
        * just:获取数据源
        * skip:跳过3项
        * take:选择2个
        * map:处理数据类型
        * */
        Flowable.just("1", "2", "3", "4", "5", "6", "7")
                .skip(3)
                .take(2)
                .map { s -> "hello" + s }
                .subscribe(object : FlowableSubscriber<Any> {
                    override fun onSubscribe(@NonNull s: Subscription) {
                        s.request(Long.MAX_VALUE)
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
