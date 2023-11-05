package com.hungvv.base_mvvm_android_3

import android.app.Application
import com.hungvv.base_mvvm_android_3.di.apiServiceModule
import com.hungvv.base_mvvm_android_3.di.dataSourceModule
import com.hungvv.base_mvvm_android_3.di.mmkvModule
import com.hungvv.base_mvvm_android_3.di.networkModule
import com.hungvv.base_mvvm_android_3.di.objectBoxModule
import com.hungvv.base_mvvm_android_3.di.repositoryModule
import com.hungvv.base_mvvm_android_3.di.useCaseModule
import com.hungvv.base_mvvm_android_3.di.viewModelModule
import com.tieuvy.android.base.Logg
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


/**
- Create by :Vy Hùng
- Create at :31,October,2023
 **/

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Logg.initLogger(BuildConfig.DEBUG)
        startKoin {
            androidContext(this@MyApplication)
            modules(
                objectBoxModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                apiServiceModule,
                mmkvModule,
                networkModule,
                viewModelModule
            )
        }
    }
}