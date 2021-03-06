package com.component.module_login.http

import com.component.module_basis.http.model.ResponseBean
import retrofit2.http.*

interface LoginApiService {

    companion object{
        const val baseUrl = "http://192.168.1.5:8089/"
    }

//    @FormUrlEncoded
//    @POST("api/user/login")
//    suspend fun login(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): ResponseBean<ResUser>

    @GET("api/user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): ResponseBean<ResUser>

    @GET("api/user/login")
    suspend fun login(
        @Header("token") token: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): ResponseBean<ResUser>

}