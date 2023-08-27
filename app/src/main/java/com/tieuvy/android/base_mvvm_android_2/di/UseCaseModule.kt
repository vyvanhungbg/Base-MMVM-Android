package com.tieuvy.android.base_mvvm_android_2.di

import com.tieuvy.android.base_mvvm_android_2.data.use_case.SaveResultOfSearchByNameUserCase
import com.tieuvy.android.base_mvvm_android_2.data.use_case.SearchUserByNameLocalUserCase
import com.tieuvy.android.base_mvvm_android_2.data.use_case.SearchUserByNameOnlineUserCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchUserByNameOnlineUserCase(get()) }
    single { SearchUserByNameLocalUserCase(get()) }
    single { SaveResultOfSearchByNameUserCase(get()) }
}