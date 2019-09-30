package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.exception.ApiException
import com.kotlin.base.exception.BaseException
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    Boolean类型转换封装
 */
class BaseFuncBoolean<T>: Function<BaseResp<T>, Observable<Boolean>> {
    override fun apply(t: BaseResp<T>): Observable<Boolean> {
        if (t.code != ResultCode.SUCCESS){
            return Observable.error(ApiException(t.code, t.message))
        }
        if(t.message.contains("\\r\\n###")){
            return Observable.error(BaseException(t.code, "数据库异常"))
        }

        return Observable.just(true)
    }
}
