package com.component.module_basis.http.model

data class ResponseBean<out T>(val msg: String, val code: Int, val data: T)