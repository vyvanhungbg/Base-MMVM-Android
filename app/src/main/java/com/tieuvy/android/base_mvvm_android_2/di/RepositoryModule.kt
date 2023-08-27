package com.tieuvy.android.base_mvvm_android_2.di


import com.tieuvy.android.base_mvvm_android_2.data.repository.user.UserRepository
import com.tieuvy.android.base_mvvm_android_2.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(),get()) }
}