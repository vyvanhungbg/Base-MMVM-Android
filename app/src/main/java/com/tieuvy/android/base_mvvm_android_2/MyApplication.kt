package com.tieuvy.android.base_mvvm_android_2

import android.app.Application
import com.tencent.mmkv.MMKV
import com.tieuvy.android.base_mvvm_android_2.di.dataSourceModule
import com.tieuvy.android.base_mvvm_android_2.di.databaseModule
import com.tieuvy.android.base_mvvm_android_2.di.networkModule
import com.tieuvy.android.base_mvvm_android_2.di.objectBoxModule
import com.tieuvy.android.base_mvvm_android_2.di.repositoryModule
import com.tieuvy.android.base_mvvm_android_2.di.useCaseModule
import com.tieuvy.android.base_mvvm_android_2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        startKoin {
            androidContext(this@MyApplication)
            modules(
                objectBoxModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                databaseModule,
                networkModule
            )
        }
    }
}