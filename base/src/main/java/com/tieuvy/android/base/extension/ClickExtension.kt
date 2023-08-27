package com.tieuvy.android.base.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View

fun View.setPreventDoubleClick(debounceTime: Long = 300, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - lastClickTime < debounceTime) return
            action.invoke()
            lastClickTime = System.currentTimeMillis()
        }
    })
}

fun Context.openBrowser(url: String) {
    var url = url
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://$url"
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        startActivity(browserIntent)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

private var lastClickTime: Long = 0