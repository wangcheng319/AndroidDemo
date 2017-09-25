package vico.xin.mvpdemo.activity

import android.app.Activity
import android.os.Bundle

import java.util.ArrayList

/**
 * Created by wangc on 2017/5/23
 * E-MAIL:274281610@QQ.COM
 */

class test : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val datas = ArrayList<String>()

        datas.add("1")
        datas.add("2")
        datas.add("3")
        datas.add("4")
        datas.add("5")

        for (i in datas.indices) {
            print(datas[i])
        }

    }


}
