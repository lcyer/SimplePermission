package com.lcyer.simplepermission

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes

class PermissionBuilder {
    private val context = SimplePermissionProvider.context!!

    private var permissions: Array<out String> = emptyArray()

    private var denyTitle: CharSequence = ""

    private var denyMessage: CharSequence = ""

    var permissionListener: PermissionListener? = null

    fun denyTitle(denyTitle: String): PermissionBuilder {
        this.denyTitle = denyTitle
        return this
    }

    fun denyTitle(
        @StringRes denyTitle: Int
    ): PermissionBuilder {
        this.denyTitle = context.getString(denyTitle)
        return this
    }

    fun denyMessage(denyMessage: String): PermissionBuilder {
        this.denyMessage = denyMessage
        return this
    }

    fun denyMessage(
        @StringRes denyMessage: Int
    ): PermissionBuilder {
        this.denyMessage = context.getString(denyMessage)
        return this
    }

    fun permissions(vararg permission: String): PermissionBuilder {
        permissions = permission
        return this
    }

    internal fun check() {
        when {
            permissionListener == null -> throw IllegalArgumentException("PermissionListener is absolute requirement.")
            permissions.isEmpty() -> throw IllegalArgumentException("Permissions is a required field.")
        }

        SimplePermissionActivity.startActivity(
            context = context,
            intent = context.permissionIntent(),
            permissionListener = permissionListener
        )
    }

    private fun Context.permissionIntent() = Intent(
        this,
        SimplePermissionActivity::class.java
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(Constants.PERMISSIONS, permissions)
        putExtra(Constants.DENY_TITLE, denyTitle)
        putExtra(Constants.DENY_MESSAGE, denyMessage)
    }

    fun create(): SimplePermission = SimplePermission
}