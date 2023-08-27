package com.tieuvy.android.base_mvvm_android_2.di

import com.tieuvy.android.base_mvvm_android_2.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get(),get()) }
}