package com.component.module_login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.component.module_basis.RoutePath
import com.component.module_basis.base.BaseActivity
import com.component.module_basis.toast
import com.component.module_login.data.LoginRepository
import com.component.module_login.data.LoginViewModel
import com.component.module_login.http.ResUser
import com.tencent.mmkv.MMKV
import com.component.module_basis.dialog.DialogHandle
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = RoutePath.pageLogin)
class LoginActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProvider(
            this,
            Injector.getLoginRepository(LoginRepository())
        ).get(LoginViewModel::class.java)
    }
    private val defaultMMKV by lazy {
        MMKV.defaultMMKV()
    }



    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {


        val name = defaultMMKV.getString("LoginUserName", "")
        if (!TextUtils.isEmpty(name)) {
            loginUserName.setText(name)
            val pwd = defaultMMKV.getString("LoginPassword", "")
            loginPassword.setText(pwd)
        }

        loginUserName.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            loginUserNameLayout.isErrorEnabled = false
        })
        loginPassword.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            loginPasswordLayout.isErrorEnabled = false
        })

//        loginRegistered.visibility = View.GONE
        loginRegistered.setOnClickListener {
            startActivity(Intent(this,RegisteredActivity::class.java))
        }

        loginForget.setOnClickListener {
            startActivity(Intent(this,ForgetActivity::class.java))
        }

        jumpModuleMain.setOnClickListener {
            verification()
        }
    }

    override fun initData() {
    }

    private fun verification() {

        DialogHandle.loadingShow(this)

        val mUserName = loginUserName.text.toString()
        if (TextUtils.isEmpty(mUserName)) {
            loginUserNameLayout.error = resources.getString(R.string.login_username_empty)
            DialogHandle.loadingDismiss()
            return
        }

        val mPassword = loginPassword.text.toString()
        if (TextUtils.isEmpty(mPassword)) {
            loginPasswordLayout.error = resources.getString(R.string.login_password_empty)
            DialogHandle.loadingDismiss()
            return
        }

        doLogin(mUserName, mPassword)
    }

    private fun doLogin(mUserName: String, mPassword: String) {
//        mViewModel.login("admin", "123abc")
        mViewModel.login(mUserName, mPassword)
            .observe(this,
                {
                    saveLoginInfo(it)
                    DialogHandle.loadingDismiss()
                    ARouter.getInstance().build(RoutePath.pageMain).navigation()
                    finish()
                })

        mViewModel.showWarningMsg().observe(this, {
            DialogHandle.loadingDismiss()
            toast(it)
        })
    }

    private fun saveLoginInfo(resUser: ResUser) {
        val name = defaultMMKV.getString("LoginUserName", "")
        if (TextUtils.isEmpty(name) || !TextUtils.equals(name, resUser.username)) {
            defaultMMKV.putString("LoginUserName", resUser.username)
            defaultMMKV.putString("LoginPassword", resUser.password)
            defaultMMKV.putString("LoginToken", resUser.token)
        }
    }
}