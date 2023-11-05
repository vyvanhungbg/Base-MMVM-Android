package com.hungvv.base_mvvm_android_3.di

import android.R
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVHandler
import com.tencent.mmkv.MMKVLogLevel
import com.tencent.mmkv.MMKVRecoverStrategic
import org.koin.dsl.module
import timber.log.Timber


/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

private const val TAG = "MMKVModule"
val mmkvModule = module {
    single { provideMMKV(get()) }
}

fun provideMMKV(context: Context): MMKV {
    MMKV.initialize(context)
    return MMKV.defaultMMKV()
}