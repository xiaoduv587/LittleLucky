package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.exception.ApiException
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BaseTestFunc<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.code != ResultCode.SUCCESS) {
            return Observable.error(ApiException(t.code, t.message))
        }
        if (t.result != ResultCode.RESULT) {
            return Observable.error(ApiException(t.code, t.message))
        }

        return Observable.just(t.data)
    }

}
