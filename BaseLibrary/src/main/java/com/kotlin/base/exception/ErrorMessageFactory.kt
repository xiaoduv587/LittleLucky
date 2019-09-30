package com.kotlin.base.exception


/**
 * 收集错误信息
 */
object ErrorMessageFactory {

    fun create(code: Int): String {

        val errorMsg: String = when (code) {
            BaseException.HTTP_ERROR -> "网络连接错误"
            BaseException.SOCKET_TIMEOUT_ERROR -> "网络超时"
            BaseException.SOCKET_ERROR -> "网络不可用"
            BaseException.ERROR_HTTP_400 -> "传参错误"
            BaseException.ERROR_HTTP_404 -> "服务器资源找不到"
            BaseException.ERROR_HTTP_500 -> "服务器错误"
            BaseException.ERROR_HTTP_502 -> "项目没有启动"
            BaseException.ERROR_API_SYSTEM -> "服务器出错"
            BaseException.ERROR_API_NO_PERMISSION -> "调用了无权限的API接口"
            BaseException.ERROR_TOKEN -> "登录失效,请重新登录"
            BaseException.JSON_ERROR->"数据异常"
            else -> "网络不可用"
        }

        return errorMsg


    }


}
