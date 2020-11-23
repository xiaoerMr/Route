package com.component.module_login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.component.module_basis.RoutePath
import com.component.module_basis.base.BaseActivity
import com.component.module_basis.toast
import com.component.module_login.data.LoginRepository
import com.component.module_login.data.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = RoutePath.pageLogin)
class LoginActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProvider(
            this,
            Injector.getLoginRepository(LoginRepository())
        ).get(LoginViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

        jumpModuleMain.setOnClickListener {
            toast("this is login")
            ARouter.getInstance().build(RoutePath.pageMain).navigation()
        }

        mViewModel.login("admin", "123abc")
            .observe(this,
                {
                    toast(it.toString())
                })

        mViewModel.showWarningMsg().observe(this,{toast(it)})
    }

    override fun initData() {

    }
}