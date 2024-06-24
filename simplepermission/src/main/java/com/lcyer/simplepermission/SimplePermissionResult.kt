package com.lcyer.simplepermission

data class SimplePermissionResult(
    val isGranted: Boolean = false,
    val deniedPermissions: List<String> = emptyList()
)
