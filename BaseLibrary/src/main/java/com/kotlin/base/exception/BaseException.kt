package com.kotlin.base.exception


/*
    定义通用异常
 */
open class BaseException() : Exception() {

    var code: Int = 0
    var msg: String = ""


    constructor(code: Int, msg: String) : this() {
        this.code = code
        this.msg = msg
    }

    companion object {
        /*API错误*/
        const val API_ERROR = 0x0
        /*网络错误*/
        const val NETWORD_ERROR = 0x1
        /*http_错误*/
        const val HTTP_ERROR = 0x2
        /*json错误*/
        const val JSON_ERROR = 0x3
        /*未知错误*/
        const val UNKNOWN_ERROR = 0x4
        /*运行时异常-包含自定义异常*/
        const val RUNTIME_ERROR = 0x5
        /*无法解析该域名*/
        const val UNKOWNHOST_ERROR = 0x6

        /*连接网络超时*/
        const val SOCKET_TIMEOUT_ERROR = 0x7

        /*无网络连接*/
        const val SOCKET_ERROR = 0x8


        //    api /////////////////////////////////////////

        // 服务器错误
        const val ERROR_API_SYSTEM = 10000

        // 登录错误，用户名密码错误
        const val ERROR_API_LOGIN = 10001

        //调用无权限的API
        const val ERROR_API_NO_PERMISSION = 10002



        //Token 失效
        const val ERROR_TOKEN = 2

        // http

        const val ERROR_HTTP_400 = 400

        const val ERROR_HTTP_404 = 404

        const val ERROR_HTTP_405 = 405

        const val ERROR_HTTP_500 = 500

        const val ERROR_HTTP_502 = 502


    }


}

