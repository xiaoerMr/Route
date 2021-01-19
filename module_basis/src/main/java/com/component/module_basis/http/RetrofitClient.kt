package com.component.module_basis.http

import com.component.module_basis.BuildConfig
import com.component.module_basis.http.interceptor.CommonInterceptor
import com.component.module_basis.http.interceptor.LogInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private var retrofit: Retrofit

    companion object {
        val instance: RetrofitClient by lazy { RetrofitClient() }
    }

    init {
        retrofit = Retrofit.Builder()
            .client(initClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiService.baseUrl)
            .build()
    }

    fun <T> onCreateApiService(service: Class<T>): T {
        return retrofit.create(service)
    }

    private fun initClient(): OkHttpClient {
        val log = HttpLoggingInterceptor(LogInterceptor())

        if (BuildConfig.DEBUG)  log.level = HttpLoggingInterceptor.Level.BODY
        else  log.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(log)
            .addInterceptor(CommonInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }


//    private fun initLogInterceptor(): HttpLoggingInterceptor {
//        val logging = HttpLoggingInterceptor()
//
//        if (BuildConfig.DEBUG) logging.level = HttpLoggingInterceptor.Level.BODY
//        else logging.level = HttpLoggingInterceptor.Level.BASIC
//        return logging
//    }

}