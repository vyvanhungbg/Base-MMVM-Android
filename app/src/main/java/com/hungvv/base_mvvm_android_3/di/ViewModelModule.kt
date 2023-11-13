package com.hungvv.base_mvvm_android_3.di

import com.hungvv.base_mvvm_android_3.presentation.detail.DetailViewModel
import com.hungvv.base_mvvm_android_3.presentation.home.HomeViewModel
import com.hungvv.base_mvvm_android_3.presentation.home_epoxy.HomeEpoxyViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get(),get(),get()) }
    viewModel { HomeEpoxyViewModel(get(),get(),get(),get()) }
    viewModel { DetailViewModel() }
}