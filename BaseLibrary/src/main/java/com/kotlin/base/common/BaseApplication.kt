package com.kotlin.base.common

import android.content.Context
import android.graphics.Color
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ColorUtils
import com.kongzue.dialog.util.DialogSettings
import com.kongzue.dialog.util.TextInfo
import com.kotlin.base.injection.component.AppComponent
import com.kotlin.base.injection.component.DaggerAppComponent
import com.kotlin.base.injection.module.AppModule
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits
import org.litepal.LitePalApplication


/*
    Application 基类
 */
open class BaseApplication : LitePalApplication() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this

        //ARouter初始化
//        ARouter.openLog()    // 打印日志
//        ARouter.openDebug()
//        ARouter.init(this)


        //字体适配
        AutoSizeConfig.getInstance()
                .setLog(true)
                .setBaseOnWidth(false)
                .unitsManager.supportSubunits = Subunits.NONE

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(com.kotlin.base.R.color.gary_FAFAFA, com.kotlin.base.R.color.dealer_common_text_gray)//全局设置主题颜色
            ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }

        //统一dialog样式
        initDialog()


    }

    /**
     * 初始化dialog样式
     */
    private fun initDialog() {
        //ios风格
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS
        //设置dialog亮色
        DialogSettings.theme=DialogSettings.THEME.LIGHT
        DialogSettings.buttonTextInfo = TextInfo().setFontColor(Color.BLACK)
        DialogSettings.buttonPositiveTextInfo = TextInfo().setFontColor(ColorUtils.string2Int("#0088fb"))

    }

    /*
            Application Component初始化
         */
    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /*
            全局伴生对象
         */
    companion object {
        lateinit var context: Context
    }

}
