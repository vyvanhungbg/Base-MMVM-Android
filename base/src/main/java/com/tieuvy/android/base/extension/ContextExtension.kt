package com.tieuvy.android.base.extension

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(resource: Int) {
    Toast.makeText(this, resource, Toast.LENGTH_SHORT).show()
}

fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.drawable(@DrawableRes drawable: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawable)
}

fun Context?.openMarket(packageName: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    kotlin.runCatching {
        intent.data = Uri.parse("market://details?id=$packageName")
        this?.startActivity(intent)
    }
}


fun Context.sendShareApp(applicationId: String) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "getString(R.string.app_name)")
        var shareMessage = "Download app " + "getString(R.string.app_name)"
        shareMessage = "$shareMessage https://play.google.com/store/apps/details?id=$applicationId".trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}




fun Context.isNetworkAvailable(): Boolean {
    val cm: ConnectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val cap: NetworkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else
        for (n in cm.allNetworks) {
            val nInfo: NetworkInfo? = cm.getNetworkInfo(n)
            if (nInfo != null && nInfo.isConnected) return true
        }
    return false
}

fun Context.copyAssetFile(assetPath: String, destPath: String): File {
    val destFile = File(destPath)
    destFile.parent?.let {
        File(it).mkdirs()
        destFile.createNewFile()

        assets.open(assetPath).use { src ->
            FileOutputStream(destFile).use { dest ->
                src.copyTo(dest)
            }
        }
    }
    return destFile
}