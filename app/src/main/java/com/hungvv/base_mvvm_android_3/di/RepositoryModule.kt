package com.hungvv.base_mvvm_android_3.di

import com.hungvv.base_mvvm_android_3.data.repositories.user.UserRepository
import com.hungvv.base_mvvm_android_3.data.repositories.user.UserRepositoryImpl
import org.koin.dsl.module

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(),get()) }
}