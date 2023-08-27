package com.tieuvy.android.base_mvvm_android_2.di

import com.tieuvy.android.base_mvvm_android_2.data.data_source.user.UserDataSource
import com.tieuvy.android.base_mvvm_android_2.data.data_source.user.UserLocalDataSource
import com.tieuvy.android.base_mvvm_android_2.data.data_source.user.UserRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<UserDataSource.Remote> { UserRemoteDataSource(get()) }
    single<UserDataSource.Local> { UserLocalDataSource(get()) }
}