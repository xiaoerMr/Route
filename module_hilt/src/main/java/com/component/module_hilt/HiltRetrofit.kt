package com.component.module_hilt

import com.component.module_basis.http.ApiService
import com.component.module_basis.http.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltRetrofit {

    @Singleton
    @Provides
    fun provideApiService():ApiService {
        return Retrofit.Builder()
            .baseUrl("http://45.32.43.43")
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): ApiService {
        return RetrofitClient.instance.onCreateApiService(ApiService::class.java)
    }
}