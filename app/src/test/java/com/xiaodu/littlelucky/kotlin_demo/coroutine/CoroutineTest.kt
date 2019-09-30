package com.xiaodu.littlelucky.kotlin_demo.coroutine

import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

/**
 * 协程测试
 */
//开始协程
fun startCoroutine(block:suspend ()->Unit){
    block.startCoroutine((BaseContinuation()))

}

suspend fun startLoadImg(url :String) = suspendCoroutine<ByteArray> {
    println("耗时操作，下载图片")
//    try {
//
//        val responseBody = HttpService.service.getLogo(url).execute()
//        if(responseBody.isSuccessful){
//            responseBody.body()?.byteStream()?.readBytes()?.let(continuation::resume)
//        }else{
//            continuation.resumeWithException(HttpException(responseBody.code()))
//        }
//    } catch(e: Exception) {
//        continuation.resumeWithException(e)
//    }
}