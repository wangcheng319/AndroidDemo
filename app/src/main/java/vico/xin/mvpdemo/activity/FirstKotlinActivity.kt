package vico.xin.mvpdemo.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_first_kotlin.*
import vico.xin.mvpdemo.R

/**
 * kotlin基本语法
 *
 * kotlin 继承和实现都用：
 */
class FirstKotlinActivity : AppCompatActivity(),View.OnClickListener {

    //点击事件
    override fun onClick(v: View) {
        when(v.id){
            R.id.action_bar ->Log.d("","this is R.id.action_bar")
            R.id.action0  ->Log.d("","this is R.id.action0")
            R.id.action_bar_root ->Log.d("","R.id.action_bar_root")
            R.id.action_bar_title ->finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_kotlin)

        //替代java中的findviewbyid settext
        tv_test.text = "hello kotlin"
        tv_test.textSize = 18F

        var btn :Button = container.getChildAt(1) as Button
        btn.text.length
        btn.background = resources.getDrawable(R.id.icon)

        //单个设置点击事件
        tv_test.setOnClickListener { finish() }


        //函数-含返回值
         val  c : Int = add(1,2)
         Log.d("", "this is " + c)

        //函数-不含返回值
        showResult(c)

        //函数-返回Unit
        showResultWithUnit()


        //变量
        deFineVariable()

        //when语句
        whenTest(3)

        //数据类
        val dataClass = classTest(this,"hello")
        //数据类复制，只改变了test的值
        val dataClass1 = dataClass.copy(test = "hello1")


        //rxjava2

        Flowable.just("hello").subscribe { Log.d("he","") }

    }

    /**
     * when 类似java中的switch语句
     */
    private fun whenTest(a: Int) {
        when(a){
            0 -> Log.d("","this is 0")
            1 -> Log.e("","this is 1")
            2 -> Log.e("","this is 1")
            3 -> Log.e("","this is 1")
            4 -> Log.e("","this is 1")
        }

    }

    /**
     * 定义变量
     */
    private fun deFineVariable() {
        //定义常量
        val test :Int = 0

        val test1 = 1

        val test2 : Int

        test2 = 2


        //定义变量

        var test3 :Int = 4

        test3++

    }


    /**
     * 基本函数，带返回值
     */
    fun add(a:Int,b:Int): Int{
        return a+b
    }


    /**
     * 基本函数，不带返回值
     */
    fun showResult(c: Int) {
        Log.d("", "this is " + c)
    }


    private fun showResultWithUnit() : Unit {
        Log.d("","this is  Unit")

        val testClass = TestClass("hello")
        val string :String? = testClass.testFun()
        Log.d("",string)
    }

    /**
     * 构造类
     */
    class TestClass(name :String){
        //类的初始化必须在init函数内
        var  name1 :String? = null
        init {
            name1 = name
        }

        //公共方法
         fun testFun() : String? {
            return name1
        }

        //私有方法
        private fun testFunPrivate() :Int{
            return 1
        }

    }
}
