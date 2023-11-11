package com.atom.android.lebo.utils.extensions

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.atom.android.lebo.R
import com.atom.android.lebo.utils.constants.Constant

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(resource: Int) {
    Toast.makeText(this, resource, Toast.LENGTH_SHORT).show()
}

fun Context.textCountDown(number: String): Spannable {
    val text = getString(R.string.message_wait_for_send_request)
    val spannable = SpannableString("$text ${number}s")
    val sizeSpanOfNumber = RelativeSizeSpan(Constant.SIZE_SPAN_OF_NUMBER)
    spannable.setSpan(
        sizeSpanOfNumber,
        Constant.DEFAULT.POSITION,
        number.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    val red: Int = ContextCompat.getColor(this, R.color.color_red)
    spannable.setSpan(
        ForegroundColorSpan(red),
        text.length.inc(),
        text.length.inc() + number.length.inc(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}
