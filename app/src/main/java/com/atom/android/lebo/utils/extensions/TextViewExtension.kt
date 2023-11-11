package com.atom.android.lebo.utils.extensions

import android.widget.TextView
import com.jakewharton.rxbinding4.widget.textChanges

fun TextView.textChangesAfterTyping() = textChanges()
    .skipFirstAction()
    .ignoreFastAction()
    .withIOToMainThread()
