package com.lcyer.simplepermission

import android.annotation.SuppressLint
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("StaticFieldLeak")
object SimplePermission {
    private lateinit var permissionBuilder: PermissionBuilder

    @JvmStatic
    fun builder(): PermissionBuilder {
        permissionBuilder = PermissionBuilder()
        return permissionBuilder
    }

    fun check(permissionListener: PermissionListener) {
        this.permissionBuilder.permissionListener = permissionListener

        permissionBuilder.check()
    }

    suspend fun check() = suspendCoroutine {
        permissionBuilder.permissionListener = object : PermissionListener {
            override fun onGranted() {
                it.resume(SimplePermissionResult(isGranted = true))
            }

            override fun onDenied(deniedPermissions: List<String>) {
                it.resume(
                    SimplePermissionResult(
                        isGranted = false,
                        deniedPermissions = deniedPermissions
                    )
                )
            }
        }
        permissionBuilder.check()
    }

    suspend fun isGranted() = check().isGranted
}