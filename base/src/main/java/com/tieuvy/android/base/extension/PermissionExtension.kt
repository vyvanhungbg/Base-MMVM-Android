package com.tieuvy.android.base.extension

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Context?.checkHasPermission(namePermission: String): Boolean {
    this?.let {
        return (ActivityCompat.checkSelfPermission(
            it,
            namePermission
        ) == PackageManager.PERMISSION_GRANTED)
    } ?: kotlin.run {
        return false
    }
}

fun Context?.checkHasPermission(permissions: List<String>): Boolean {
    this?.let {
        return permissions.all{permission -> checkHasPermission(permission) }
    } ?: kotlin.run {
        return false
    }
}