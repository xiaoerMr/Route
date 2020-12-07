package com.component.module_basis.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Builder(private val permissionUtils: PermissionUtils) {

    private lateinit var activity: FragmentActivity
    private lateinit var fragment: Fragment
    private lateinit var permissions: List<String>
    private lateinit var requestReason: String

    fun requestReason(msg: String) : Builder{
        requestReason = msg
        return this
    }
    fun getRequestReason() = requestReason

    fun activity(activity: FragmentActivity): Builder {
        this.activity = activity
        return this
    }

    fun getActivity() = activity

    fun fragment(fragment: Fragment): Builder {
        this.fragment = fragment
        return this
    }

    fun getFragment() = fragment

    fun permissions(permissions: List<String>): PermissionUtils {
        this.permissions = permissions
        return permissionUtils
    }

    fun permissions(vararg permissions: String): PermissionUtils {
        this.permissions = permissions.asList()
        return permissionUtils
    }

    fun getPermissions() = permissions

//  fun budler():PermissionUtils{
//      return permissionUtils
//  }
}