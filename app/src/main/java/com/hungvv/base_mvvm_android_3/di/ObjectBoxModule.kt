package com.hungvv.base_mvvm_android_3.di

import android.content.Context
import android.util.Log
import com.hungvv.base_mvvm_android_3.BuildConfig
import com.hungvv.base_mvvm_android_3.data.model.MyObjectBox
import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import io.objectbox.BoxStore
import io.objectbox.android.Admin
import org.koin.dsl.module
import timber.log.Timber

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

val objectBoxModule = module {
    single { provideObjectBox(get()) }
    single { provideBoxSearchLocal(get()) }
}

fun provideObjectBox(context: Context): BoxStore {

    val boxStore = MyObjectBox.builder()
        .androidContext(context.applicationContext)
        .build()
    if (BuildConfig.DEBUG) {
        val started = Admin(boxStore).start(context)
        Timber.e("ObjectBoxAdmin Started: $started")
    }
    return boxStore
}

fun provideBoxSearchLocal(boxStore: BoxStore)= boxStore.boxFor(SearchCacheLocal::class.java)