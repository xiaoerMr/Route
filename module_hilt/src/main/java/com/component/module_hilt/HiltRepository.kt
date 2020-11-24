package com.component.module_hilt

import com.component.module_basis.http.ApiService
import javax.inject.Inject

class HiltRepository @Inject constructor(){

    @Inject
    lateinit var apiService: ApiService

    fun test (){
        println("-- 这里是 HiltRepository -- apiService.hashCode = ${apiService.hashCode()}")
    }

}