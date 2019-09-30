package com.kotlin.base.exception

/**
 * api的错误信息
 */
class ApiException(code: Int, msg: String) : BaseException(code, msg)
