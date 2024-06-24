package com.lcyer.simplepermission

interface PermissionListener {
    fun onGranted()

    fun onDenied(deniedPermissions: List<String>)
}