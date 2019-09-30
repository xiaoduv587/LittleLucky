package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseBean
import com.kotlin.base.exception.ApiException
import com.kotlin.base.exception.BaseException
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BaseBeanFunc : Function<BaseBean, Observable<String>> {
    override fun apply(t: BaseBean): Observable<String> {
        if (t.code != ResultCode.SUCCESS) {
            return Observable.error(ApiException(t.code, t.message))
        }
        if (!t.isResult) {
            return Observable.error(ApiException(t.code, t.message))
        }
        if(t.message.contains("\\r\\n###")){
            return Observable.error(BaseException(t.code, "数据库异常"))
        }

        return Observable.just(t.message)
    }
}
