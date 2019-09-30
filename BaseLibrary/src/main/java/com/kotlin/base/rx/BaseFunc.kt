package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.exception.ApiException
import com.kotlin.base.exception.BaseException
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BaseFunc<T> : Function<BaseResp<T>, Observable<T>> {


    override fun apply(t: BaseResp<T>): Observable<T> {

        if (t.code != ResultCode.SUCCESS) {
            return Observable.error(ApiException(t.code, t.message))
        }
        if (t.result != ResultCode.RESULT) {
            return Observable.error(ApiException(t.code, t.message))
        }


        if (t.message.contains("\\r\\n###")) {
            return Observable.error(BaseException(t.code, "数据库异常"))
        }

        if (t.data == null) {
            return Observable.error(DataNullException())
        }


        return Observable.just(t.data)
    }
}
