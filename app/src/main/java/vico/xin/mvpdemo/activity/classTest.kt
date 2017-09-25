package vico.xin.mvpdemo.activity

import android.content.Context

/**
 * Created by wangc on 2017/5/23
 * E-MAIL:274281610@QQ.COM
 *
 * 数据类
 */

data class classTest(private val mContext: Context, private val test: String) {


    fun test() {
        print("hello")
    }

    fun testString(): String {

        return ""
    }

    fun testBack(a: String, b: String): String {
        return a + b
    }


}
