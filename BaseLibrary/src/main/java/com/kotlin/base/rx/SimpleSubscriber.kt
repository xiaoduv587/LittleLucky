package com.kotlin.base.rx

import com.kotlin.base.exception.BaseException
import com.kotlin.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*
    Rx订阅者默认实现
 */
open class SimpleSubscriber<T> : Observer<T> {



    override fun onSubscribe(p0: Disposable) {
    }


    override fun onComplete() {
    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {

    }
}
