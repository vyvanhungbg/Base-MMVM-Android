package com.atom.android.lebo.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN


fun Activity.closeKeyboard() {
    currentFocus?.let {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
        manager?.hideSoftInputFromWindow(it.windowToken, RESULT_UNCHANGED_SHOWN)
    }
}

fun Activity.showKeyboard(view: View) {
    view.requestFocus()
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, RESULT_UNCHANGED_SHOWN)
}
