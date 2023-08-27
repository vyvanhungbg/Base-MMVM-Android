package com.tieuvy.android.base_mvvm_android_2.di

import android.content.Context
import com.tieuvy.android.base_mvvm_android_2.data.storage.local.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }

}

fun provideDatabase(context: Context): AppDatabase {
    return AppDatabase.getInstance(context.applicationContext)
}


