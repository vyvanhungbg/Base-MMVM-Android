package com.atom.android.lebo.data.di

import android.app.Application
import android.content.SharedPreferences
import com.atom.android.lebo.utils.constants.Constant
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single {
        getSharedPrefs(androidApplication())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        Constant.SHARED_PREF_ROOT,
        android.content.Context.MODE_PRIVATE
    )
}
