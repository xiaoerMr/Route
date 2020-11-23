package com.component.module_login.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.component.module_basis.http.model.NetRequestResult.Success
import com.component.module_basis.http.model.NetRequestResult.Error
import com.component.module_login.http.ResUser
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val loginLiveData = MutableLiveData<ResUser>()
    private val warningMsgData = MutableLiveData<String>()

    fun login(username: String, password: String): MutableLiveData<ResUser> {
        viewModelScope.launch {
            //执行请求 并返回结果
            val doLogin = repository.doLogin(username,password)

            // 成功
            if (doLogin is Success) {
                loginLiveData.postValue(doLogin.data)
            }else{
                doLogin as Error
                warningMsgData.postValue(doLogin.exception.errorMsg)
                Log.e("--LoginViewModel", "login: ${doLogin.exception.errorCode},${doLogin.exception.errorMsg}" )
            }
        }
        return loginLiveData
    }

    /**
     * 显示警告信息内容
     */
    fun showWarningMsg():MutableLiveData<String> = warningMsgData

}