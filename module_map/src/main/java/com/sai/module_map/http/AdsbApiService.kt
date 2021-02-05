package com.sai.module_map.http

import com.component.module_basis.http.model.ResponseBean
import retrofit2.http.*

interface AdsbApiService {
    companion object{
//        const val baseUrl = "http://39.97.235.83:8089/"
        const val baseUrl = "http://192.168.1.5:8089/"
    }

    @GET("api/adsb/findAll")
    @Headers("token:eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NCJ9.ndz2QSZi4Sgy3rQVp74uQidgft74YjCZCusMOyJXWWE")
    suspend fun getAdsbData(): ResponseBean<MutableList<ResAdsbData>>

    @POST("api/adsb/inset")
    @Headers("token:eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NCJ9.ndz2QSZi4Sgy3rQVp74uQidgft74YjCZCusMOyJXWWE")
    suspend fun sendAdsbData(@Body data:ResAdsbData)
}