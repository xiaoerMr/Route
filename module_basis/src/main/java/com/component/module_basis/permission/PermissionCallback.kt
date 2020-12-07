package com.component.module_basis.permission

interface PermissionCallback {
    fun onAllGranted()
    fun onDeniedList(denied: List<String>)
}