package com.kotlin.base.ui.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.kotlin.base.R
import com.kotlin.base.common.AppManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import qiu.niorgai.StatusBarCompat

/*
    Activity基类，业务无关
 */
open abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutResID())

        init()

        setStatusBar()

        AppManager.instance.addActivity(this)

    }

    @LayoutRes
    protected abstract fun setLayoutResID(): Int

    protected abstract fun init() :Unit


    //更换title bar 颜色
    protected open fun setStatusBar() {
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.dealer_main_title))
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
//    val contentView: View
//        get() {
//            val content = find<FrameLayout>(android.R.id.content)
//            return content.getChildAt(0)
//        }

}
