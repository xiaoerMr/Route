package com.component.module_hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class HiltRetrofit {

//    @Singleton
//    @Provides
//    fun provideRetrofit(client: OkHttpClient):Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("http://45.32.43.43")
//            .client(client)
//            .build()
//    }

//    @Singleton
//    @Provides
//    fun provideOkHttp(): OkHttpClient {
//        return OkHttpClient.Builder().build()
//    }
}