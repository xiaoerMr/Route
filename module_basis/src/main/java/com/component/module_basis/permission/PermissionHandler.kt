package com.component.module_basis.permission

import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import java.lang.RuntimeException

class PermissionHandler {

    private val forwardToSettings = "您需要去设置当中同意请求的权限"
    private lateinit var buidler: Builder

    /**
     * 构建请求权限结构，首先调用该方法
     * 最后调用 callback（） 方法
     */
    fun create(): Builder {
        buidler = Builder(this)
        return buidler
    }



    /**
     * 请求权限的回调
     */
    fun callback(callback: PermissionCallback) {

        if (buidler == null) {
            throw RuntimeException(" -- PermissionUtils -- 请先调用 create（）方法")
        }

        if (buidler.getActivity() == null && buidler.getFragment() == null) {
            throw RuntimeException(" -- PermissionUtils -- 请设置 Activity 或 Fragment")
        }

        if (buidler.getPermissions() == null || buidler.getPermissions().isEmpty()) {
            throw RuntimeException(" -- PermissionUtils -- 请设置请求的权限")
        }
        if (TextUtils.isEmpty(buidler.getRequestReason())) {
            throw RuntimeException(" -- PermissionUtils -- 请设置 requestReason 请求原因")
        }

        if (buidler.getActivity() != null) {
            show(
                activity = buidler.getActivity(),
                permissions = buidler.getPermissions(),
                requestReason = buidler.getRequestReason(),
                callback = callback
            )
        } else {
            show(
                fragment = buidler.getFragment(),
                permissions = buidler.getPermissions(),
                requestReason = buidler.getRequestReason(),
                callback = callback
            )
        }
    }

    /**
     *  检查单个权限是否允许
     */
    fun checkPermissions(context: Context, permission: String): Boolean {
        return PermissionX.isGranted(context, permission)
    }

    private fun show(
        fragment: Fragment,
        permissions: List<String>,
        requestReason: String,
        callback: PermissionCallback
    ) {
        PermissionX
            .init(fragment)
            .permissions(permissions)
            .onExplainRequestReason { scope, deniedList ->
                // 解释请求原因
                scope.showRequestReasonDialog(deniedList, requestReason, "确定")
            }
            .onForwardToSettings { scope, deniedList ->
                // 手动设置
                scope.showForwardToSettingsDialog(deniedList, forwardToSettings, "确定")
            }
            .request { allGranted, _, deniedList ->

                if (allGranted) callback.onAllGranted()
                else callback.onDeniedList(deniedList)

            }
    }

    private fun show(
        activity: FragmentActivity,
        permissions: List<String>,
        requestReason: String,
        callback: PermissionCallback
    ) {
        PermissionX
            .init(activity)
            .permissions(permissions)
            .onExplainRequestReason { scope, deniedList ->
                // 解释请求原因
                scope.showRequestReasonDialog(deniedList, requestReason, "确定")
            }
            .onForwardToSettings { scope, deniedList ->
                // 手动设置
                scope.showForwardToSettingsDialog(deniedList, forwardToSettings, "确定")
            }
            .request { allGranted, _, deniedList ->

                if (allGranted) callback.onAllGranted()
                else callback.onDeniedList(deniedList)
            }
    }
}