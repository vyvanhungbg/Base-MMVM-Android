package com.atom.android.lebo.utils.extensions

fun <T> List<T>?.hasItem(): List<T> {
    if (this.isNullOrEmpty()) {
        return mutableListOf()
    }
    return map { it }
}
