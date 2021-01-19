package com.component.module_basis.http.interceptor

import android.util.Log
import com.component.module_basis.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

class LogInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (BuildConfig.DEBUG) {
            Log.e("-- Http --",message)
        }
    }
}