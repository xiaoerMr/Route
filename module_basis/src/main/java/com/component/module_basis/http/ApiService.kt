package com.component.module_basis.http

interface ApiService {

    companion object{
        //此处的参数可在别处定义（常量）
        // 服务器请求地址
        const val baseUrl = "http://39.97.235.83:8089/"
        // 服务器定义的请求成功码
        const val successCode = 200
    }
}