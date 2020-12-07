package com.component.route

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.component.module_basis.base.BaseActivity
import com.component.module_basis.RoutePath
import com.component.module_basis.dialog.DialogHandle
import com.component.module_basis.toast
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RoutePath.pageMain)
class MainActivity : BaseActivity() {

    private val defaultMMKV by lazy {
        MMKV.defaultMMKV()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        showToolBar("主标题")

        jumpModuleLogin.setOnClickListener {
            toast("this is main")
            ARouter.getInstance().build(RoutePath.pageLogin).navigation()
        }

        jumpModuleHilt.setOnClickListener {
            toast("this is main")
            ARouter.getInstance().build(RoutePath.pageHilt).navigation()
        }

        jumpDialog.setOnClickListener {
            DialogActivity.jumpDialogActivity(this)
        }
//        DialogHandle.loading(this)
    }

    override fun initData() {

    }
}