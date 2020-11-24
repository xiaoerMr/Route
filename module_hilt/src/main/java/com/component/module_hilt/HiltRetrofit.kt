package com.component.module_hilt

import com.component.module_basis.http.ApiService
import com.component.module_basis.http.RetrofitClient
import com.component.module_basis.http.interceptor.CommonInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltRetrofit {

    //两个方法都可以，效果相同; 但 OkHttpClient注入会编译失败，不知道为什么
//   错误信息： 无法访问OkHttpClient
//            找不到okhttp3.OkHttpClient的类文件

//    @Singleton
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//          return OkHttpClient.Builder()
////        .addInterceptor(initLogInterceptor())
//        .addInterceptor(CommonInterceptor())
//        .connectTimeout(10, TimeUnit.SECONDS)
//        .readTimeout(10, TimeUnit.SECONDS)
//        .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideApiService(okClient: OkHttpClient): ApiService {
//        return Retrofit.Builder()
//            .baseUrl("https://45.32.43.43")
//            .client(okClient)
//            .build()
//            .create(ApiService::class.java)
//    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://45.32.43.43")
            .build()
            .create(ApiService::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideRetrofit(): ApiService {
//        return RetrofitClient.instance.onCreateApiService(ApiService::class.java)
//    }
}