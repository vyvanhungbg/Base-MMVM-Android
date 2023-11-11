package com.atom.android.lebo.utils.extensions

import android.content.SharedPreferences
import com.atom.android.lebo.utils.constants.Constant

fun SharedPreferences.getTokenLogin(): String? {
    return this.getString(Constant.SHARED_PREF_TOKEN_LOGIN, Constant.SHARED_PREF_DEFAULT_STRING)
}

fun SharedPreferences.saveTokenLogin(token: String) {
    this.edit().putString(Constant.SHARED_PREF_TOKEN_LOGIN, token).apply()
}

fun SharedPreferences.destroyTokenLogin(): Boolean {
    return this.edit().remove(Constant.SHARED_PREF_TOKEN_LOGIN).commit()
}
