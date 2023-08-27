package com.tieuvy.android.base_mvvm_android_2.di

import android.content.Context
import android.util.Log
import com.tieuvy.android.base_mvvm_android_2.data.model.MyObjectBox
import com.tieuvy.android.base_mvvm_android_2.data.model.SearchCacheLocal
import io.objectbox.BoxStore
import io.objectbox.android.Admin
import org.koin.dsl.module


val objectBoxModule = module {
    single { provideObjectBox(get()) }
    single { provideBoxSearchLocal(get()) }
}

fun provideObjectBox(context: Context):BoxStore {

    val boxStore = MyObjectBox.builder()
        .androidContext(context.applicationContext)
        .build()
    if (true) {
        val started = Admin(boxStore).start(context)
        Log.i("ObjectBoxAdmin", "Started: $started")
    }
    return boxStore
}

fun provideBoxSearchLocal(boxStore: BoxStore)= boxStore.boxFor(SearchCacheLocal::class.java)