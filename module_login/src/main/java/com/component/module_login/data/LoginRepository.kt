package com.component.module_login.data

import com.component.module_basis.http.BaseRepository
import com.component.module_basis.http.RetrofitClient
import com.component.module_basis.http.model.NetRequestResult
import com.component.module_login.http.LoginApiService
import com.component.module_login.http.ResUser

class LoginRepository : BaseRepository() {


    private val apiService by lazy {
        val onCreateApiService = RetrofitClient.instance.onCreateApiService(LoginApiService::class.java,LoginApiService.baseUrl)
        onCreateApiService
    }

    /**
     * 执行请求（封装）
     */
    suspend fun doLogin(username: String,password: String): NetRequestResult<ResUser> {
        return callRequest(call = { requestLogin(username,password) })
    }

    /**
     * 实际执行请求
     */
    private suspend fun requestLogin(username: String,password: String): NetRequestResult<ResUser> {
        return handleResponse(apiService.login(username, password))
    }

}