package com.component.module_basis.http

import android.util.Log
import com.component.module_basis.http.exception.DealException
import com.component.module_basis.http.exception.ResultException
import com.component.module_basis.http.model.NetRequestResult
import com.component.module_basis.http.model.ResponseBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseRepository {

    /**
     * 包装 请求 处理意外错误情况  call中 只执行 handleResponse（）方法
     */
    suspend fun <T> callRequest(call: suspend () -> NetRequestResult<T>): NetRequestResult<T> {
        return try {
            //执行 传递对 call 函数
            call()
        } catch (e: Exception) {
            // 统一判断错误码
            e.printStackTrace()
            NetRequestResult.Error(DealException.handlerException(e))
        }
    }

    /**
     *  统一解析返回结果
     *   response 返回结果
     *   successBlock： 成功回调 (暂时不知道用处)
     *   errorBlock： 失败回调 (暂时不知道用处)
     */
    suspend fun <T> handleResponse(
        response: ResponseBean<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): NetRequestResult<T> {

        return coroutineScope {
            Log.w("TAG", "handleResponse: ${response.code}", )
            if (response.code == ApiService.successCode) {
                successBlock?.let { it() }
                NetRequestResult.Success(response.data)
            } else {
                errorBlock?.let { it() }
                NetRequestResult.Error(ResultException(response.code, response.error))
            }
        }
    }
}