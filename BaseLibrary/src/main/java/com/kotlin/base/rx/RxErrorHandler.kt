package com.kotlin.base.rx


import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.kotlin.base.exception.ApiException
import com.kotlin.base.exception.BaseException
import com.kotlin.base.exception.BusinessException
import com.kotlin.base.exception.ErrorMessageFactory
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * 异常信息统一处理类
 */
class RxErrorHandler {


    fun handleError(e: Throwable): BaseException {

        val exception = BaseException()

        when (e) {

            is ApiException -> exception.code = e.code
            is JsonParseException -> exception.code = BaseException.JSON_ERROR
            is JsonSyntaxException -> exception.code = BaseException.JSON_ERROR
            is HttpException -> exception.code = e.code()
            is SocketTimeoutException -> exception.code = BaseException.SOCKET_TIMEOUT_ERROR
            is SocketException -> {

            }
            else -> exception.code = BaseException.UNKNOWN_ERROR
        }
        if (e is ApiException) {
            exception.msg = e.msg
        }else if(e is BusinessException){
            exception.msg = e.msg
        }else {
            exception.msg = ErrorMessageFactory.create(exception.code)
        }

        return exception
    }

}
