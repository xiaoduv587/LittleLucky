package com.xiaodu.littlelucky.kotlin_demo.coroutine

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


class BaseContinuation : Continuation<Unit> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext


    override fun resumeWith(result: Result<Unit>) {

    }


}
