package com.component.module_hilt

import javax.inject.Inject

class HiltRepository @Inject constructor(){

    fun test (){
        println("-- 这里是 HiltRepository -- okhttp = {okhttp.hashCode()}")
    }

}