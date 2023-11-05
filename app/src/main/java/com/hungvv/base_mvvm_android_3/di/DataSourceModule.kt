package com.hungvv.base_mvvm_android_3.di

import com.hungvv.base_mvvm_android_3.data.data_source.user.UserDataSource
import com.hungvv.base_mvvm_android_3.data.data_source.user.UserLocalDataSource
import com.hungvv.base_mvvm_android_3.data.data_source.user.UserRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<UserDataSource.Remote> { UserRemoteDataSource(get()) }
    single<UserDataSource.Local> { UserLocalDataSource(get()) }
}