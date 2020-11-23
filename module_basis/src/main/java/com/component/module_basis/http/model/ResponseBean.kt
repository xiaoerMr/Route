package com.component.module_basis.http.model

data class ResponseBean<out T>(val error: String, val code: Int, val data: T)