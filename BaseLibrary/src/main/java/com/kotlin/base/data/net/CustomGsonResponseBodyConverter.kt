package com.kotlin.base.data.net

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.kotlin.base.data.protocol.BaseBean
import com.kotlin.base.exception.ApiException
import okhttp3.ResponseBody
import okhttp3.internal.Util.UTF_8
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

//CustomGsonResponseBodyConverter.java
internal class CustomGsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val response = value.string()
        //获取网络请求
        val httpStatus = gson.fromJson(response, BaseBean::class.java)
        //api是否请求失败，失败为true
        if (httpStatus.isCodeInvalid) {
            value.close()
            try {
                throw ApiException(httpStatus.code, httpStatus.message)
            } catch (e: ApiException) {
                e.printStackTrace()
            }

        }

        val contentType = value.contentType()
        val charset = if (contentType != null) contentType.charset(UTF_8) else UTF_8
        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream, charset!!)
        val jsonReader = gson.newJsonReader(reader)

        value.use {
            return adapter.read(jsonReader)
        }
    }
}