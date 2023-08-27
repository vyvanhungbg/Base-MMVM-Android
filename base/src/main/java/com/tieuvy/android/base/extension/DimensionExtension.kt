package com.tieuvy.android.base.extension

import android.content.res.Resources


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()


val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}


fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}