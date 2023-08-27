package com.tieuvy.android.base_mvvm_android_2.di

import android.util.Log
import com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client.AppClient

import org.koin.dsl.module

val networkModule = module {
    single { AppClient.provideKtorClient(get()) }

}

