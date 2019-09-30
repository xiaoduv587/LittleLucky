package com.xiaodu.littlelucky

import android.content.Context
import androidx.multidex.MultiDex
import cn.bmob.v3.Bmob
import com.didichuxing.doraemonkit.DoraemonKit
import com.kotlin.base.common.BaseApplication

/**
 *
 * @作者： xiaodu
 * @时间： 2019/4/16
 * @描述： 程序Appciation，做项目初始化工作
 *
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        //注册后端云bomb
        Bmob.initialize(this, "440e417ca6f51b397418487ee7cf4371")
        DoraemonKit.install(this)
    }
}