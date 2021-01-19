package com.component.route

import android.Manifest
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.component.module_basis.base.BaseActivity
import com.component.module_basis.RoutePath
import com.component.module_basis.permission.PermissionCallback
import com.component.module_basis.permission.PermissionHandler
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
        jumpMotion.setOnClickListener {
            MotionActivity.jumpDialogActivity(this)
        }
//        DialogHandle.loading(this)
    }

    override fun initData() {

        // 请求权限
        PermissionHandler().create()
            .activity(this)
            .requestReason("我们需要以下权限进行工作")
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA)
            .callback(
                object :PermissionCallback{
                    override fun onAllGranted() {
                        toast("权限全部通过")
                    }

                    override fun onDeniedList(denied: List<String>) {
                        toast("没有通过的权限： ${denied.toString()}")
                    }
                }
            )
    }
}