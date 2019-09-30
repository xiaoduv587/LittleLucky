package com.kotlin.base.common

import com.kotlin.base.utils.AppPrefsUtils

/*
    基础常量
 */
class BaseConstant {
    companion object {

        //图片地址
        var IMAGE_SERVER_ADDRESS = "http://c.caqp-id.com/qfycode-web-mgmt/"

        //app服务器地址
//        var APP_SERVER_ADDRESS = "http://c.caqp-id.com/"

        //app服务器地址
        var APP_SERVER_ADDRESS = Configurator.getInstance().getConfiguration<String>(ConfigKeys.API_HOST)!!


        fun getAppServer(): String {
            if (AppPrefsUtils.getString("APP_SERVER_ADDRESS") != "") {
                return AppPrefsUtils.getString("APP_SERVER_ADDRESS")
            }
            return APP_SERVER_ADDRESS
        }

        //SP表名
        const val TABLE_PREFS = "sursen"

        //Token Key
        const val KEY_SP_TOKEN = "token"
    }
}
