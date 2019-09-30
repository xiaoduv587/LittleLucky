package com.kotlin.base.data.net

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 *
 * @作者： xiaodu
 * @时间： 2019-07-18
 * @描述：重复请求拦截
 *
 */
class NoRepeatInterceptor : Interceptor {


    //Value 里面保存的是时间，我们实际业务是有用的
    private val requestIdsMap: MutableMap<String, Long> = HashMap()

    override fun intercept(chain: Interceptor.Chain): Response? {

        //请求体
        val request = chain.request()
        //
        var response: Response? = null

        //拦截处理重复的HTTP 请求,类似 防止快速点击按钮去重 可以不去处理了，全局统一处理
        val requestKey = request.toString()
        try {
            if (requestIdsMap.containsKey(requestKey)) {
                //如果是重复的请求，跑出一个自定义的错误，这个错误大家根据自己的业务定义吧
                LogUtils.e("重复请求")
                return response
            } else {
                requestIdsMap[requestKey] = System.currentTimeMillis()
                response = chain.proceed(request)

                requestIdsMap.remove(requestKey)
            }

        } catch (e: Exception) {
            requestIdsMap.remove(requestKey)
            return response
        }

        return response

    }

}