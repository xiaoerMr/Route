package com.component.module_basis.http.model

import com.component.module_basis.http.exception.ResultException

/**
 * 请求结果
 */
sealed class NetRequestResult<out T> {
    data class Success<out T >(val data: T) : NetRequestResult<T>()

    data class Error(val exception: ResultException) : NetRequestResult<Nothing>()

}