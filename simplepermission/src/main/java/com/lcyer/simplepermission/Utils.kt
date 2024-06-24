package com.lcyer.simplepermission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun Context.isGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.permissionLauncher(
    onGranted: () -> Unit,
    onDenied: (deniedPermissions: List<String>) -> Unit
) = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

    val deniedPermissions = if (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
        isGranted(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) &&
        (result.containsKey(Manifest.permission.READ_MEDIA_IMAGES) || result.containsKey(Manifest.permission.READ_MEDIA_VIDEO))
    ) {
        emptyList()
    } else {
        result.filter { permissionMap ->
            !permissionMap.value
        }.map { it.key }
    }

    if (deniedPermissions.isEmpty()) onGranted()
    else onDenied(deniedPermissions)
}

fun AppCompatActivity.activityResultLauncher(
    onResult: (ActivityResult) -> Unit
) = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { onResult(it) }

fun Activity.commonAlertDialog(
    title: String,
    message: String,
    onCancel: () -> Unit,
    onOk: () -> Unit
): AlertDialog = AlertDialog.Builder(this)
    .setTitle(title)
    .setMessage(message)
    .setNegativeButton(R.string.dialog_close) { _, _ -> onCancel() }
    .setPositiveButton(R.string.dialog_move_setting) { _, _ -> onOk() }
    .show()