package com.hungvv.base_mvvm_android_3.utils.util

import com.tencent.mmkv.MMKV
import org.koin.androidx.compose.get


/**
- Create by :Vy Hùng
- Create at :11,November,2023
 **/

object LocalKV {
    const val KEY_LAST_SEARCH = "KEY_LAST_SEARCH"
}

fun MMKV.setKeyLastSearch(key: String){
    this.putString(LocalKV.KEY_LAST_SEARCH, key)
}

fun MMKV.getKeyLastSearch() = this.getString(LocalKV.KEY_LAST_SEARCH, null)