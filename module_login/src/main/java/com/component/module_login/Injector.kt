package com.component.module_login

import com.component.module_login.data.LoginRepository
import com.component.module_login.data.ViewModelFactory

object Injector {

    /**
     *  注入资源库到 viewModel
     */
    fun getLoginRepository(repository: LoginRepository) = ViewModelFactory(repository)
}