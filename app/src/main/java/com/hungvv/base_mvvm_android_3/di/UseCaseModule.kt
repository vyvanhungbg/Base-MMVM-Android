package com.hungvv.base_mvvm_android_3.di

import com.hungvv.base_mvvm_android_3.use_case.search.SaveResultOfSearchByNameUseCase
import com.hungvv.base_mvvm_android_3.use_case.search.SearchUserByNameLocalUseCase
import com.hungvv.base_mvvm_android_3.use_case.search.SearchUserByNameOnlineUseCase
import org.koin.dsl.module

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

val useCaseModule = module {
    single { SearchUserByNameOnlineUseCase(get()) }
    single { SearchUserByNameLocalUseCase(get()) }
    single { SaveResultOfSearchByNameUseCase(get()) }
}