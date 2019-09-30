package com.kotlin.base.data.net

import com.kotlin.base.common.BaseConstant
import com.kotlin.base.common.ConfigKeys
import com.kotlin.base.common.Latte
import com.kotlin.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/*
    Retrofit工厂，单例
 */
class AppRetrofitFactory private constructor() {

    /*
        单例实现
     */
    companion object {
        val INSTANCE: AppRetrofitFactory by lazy { AppRetrofitFactory() }

    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit
    private val BUILDER = OkHttpClient.Builder()
    private val INTERCEPTORS: ArrayList<Interceptor> = Latte.getConfiguration(ConfigKeys.INTERCEPTOR)

    //Value 里面保存的是时间，我们实际业务是有用的
    private  val requestIdsMap: MutableMap<String, Long> = HashMap()

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("User-Agent", "android")
                    .addHeader("Content_Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .addHeader("_tokenApp", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                    .build()
            chain.proceed(request)
        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.getAppServer())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }


    private fun addInterceptor(): OkHttpClient.Builder {
        if (!INTERCEPTORS.isEmpty()) {
            for (interceptor in INTERCEPTORS) {
                BUILDER.addInterceptor(interceptor)
            }
        }
        return BUILDER
    }


    /*
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {

        return addInterceptor()
                .retryOnConnectionFailure(true)  //是否在网络差的时候重试
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
//                .addNetworkInterceptor(NoRepeatInterceptor())
                .addInterceptor(initLogInterceptor())
                .build()
    }


    /*
        日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }



    /*
        具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
