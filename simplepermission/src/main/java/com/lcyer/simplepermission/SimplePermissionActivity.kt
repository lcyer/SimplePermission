package com.lcyer.simplepermission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SimplePermissionActivity : AppCompatActivity() {
    private lateinit var denyTitle: String

    private lateinit var denyMessage: String

    private lateinit var permissions: Array<out String>

    private val systemSettingLauncher = activityResultLauncher { checkPermissions(true) }

    private val permissionLauncher = permissionLauncher(
        onGranted = { granted() },
        onDenied = { deniedPermissions ->

            commonAlertDialog(
                title = denyTitle,
                message = denyMessage,
                onCancel = { denied(deniedPermissions) },
                onOk = {
                    val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:$packageName")
                    }
                    systemSettingLauncher.launch(settingIntent)
                }
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        savedInstanceState?.let {
            permissions = it.getStringArray(Constants.PERMISSIONS) ?: emptyArray()
            denyTitle = it.getString(Constants.DENY_TITLE) ?: ""
            denyMessage = it.getString(Constants.DENY_MESSAGE) ?: ""
        } ?: let {
            permissions = intent.getStringArrayExtra(Constants.PERMISSIONS) ?: emptyArray()
            denyTitle = intent.getStringExtra(Constants.DENY_TITLE) ?: ""
            denyMessage = intent.getStringExtra(Constants.DENY_MESSAGE) ?: ""
        }

        checkPermissions(false)
    }

    private fun checkPermissions(fromOnActivityResult: Boolean) {
        val needPermissions = permissions.filter { !isGranted(it) }

        if (needPermissions.isEmpty()) {
            granted()
        } else if (fromOnActivityResult) {
            denied(needPermissions)
        } else {
            permissionLauncher.launch(needPermissions.toTypedArray())
        }
    }

    private fun granted() {
        permissionListener?.onGranted()
        finish()
    }

    private fun denied(deniedPermissions: List<String>) {
        permissionListener?.onDenied(deniedPermissions)
        finish()
    }

    companion object {
        private var permissionListener: PermissionListener? = null

        @JvmStatic
        fun startActivity(
            context: Context,
            intent: Intent,
            permissionListener: PermissionListener?
        ) {
            this.permissionListener = permissionListener

            context.startActivity(intent)
        }
    }
}