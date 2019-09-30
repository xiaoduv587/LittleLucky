package com.kotlin.base.rx

import com.kotlin.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*
    Rx订阅者默认实现
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Observer<T> {


    var mErrorHandler: RxErrorHandler? = null

    init {
        mErrorHandler = RxErrorHandler()
    }


    override fun onSubscribe(p0: Disposable) {
    }


    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()

        val baseException = mErrorHandler!!.handleError(e)


//        if (baseException.code == BaseException.ERROR_TOKEN) {
//            Bus.send(TokenEvent())
//        }

        baseView.onError(baseException.msg)
    }
}
