package vico.xin.mvpdemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import vico.xin.mvpdemo.R

class Main5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        var btn1 = Button(this)
        btn1.setOnClickListener { print("haha") }
        val list =  listOf<Int>(1,2,3,4,5)
        var ishave = list.contains(1)
        list.filter { i -> i>3 }
        String
    }
}
