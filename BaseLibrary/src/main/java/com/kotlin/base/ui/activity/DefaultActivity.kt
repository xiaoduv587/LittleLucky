package com.kotlin.base.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.kotlin.base.R
import com.kotlin.base.common.AppManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.find
import qiu.niorgai.StatusBarCompat

/*
    Activity基类，存放不是MVP类型的Activity
 */
open abstract class DefaultActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutResID())

        setStatusBar()

        init()

        setOnclickListener()

        //ARouter注册
//        ARouter.getInstance().inject(this)

        AppManager.instance.addActivity(this)


    }

    @LayoutRes
    protected abstract fun setLayoutResID(): Int

    protected abstract fun init() :Unit

    open fun setOnclickListener() {}


    //更换title bar 颜色
    protected open fun setStatusBar() {
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.common_black))
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }



    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LogUtils.e(newConfig)
    }

}
