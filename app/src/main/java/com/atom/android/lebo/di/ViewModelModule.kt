package com.atom.android.lebo.di

import com.atom.android.lebo.ui.account.AccountViewModel
import com.atom.android.lebo.ui.authentication.forgotpassword.ForgotPasswordViewModel
import com.atom.android.lebo.ui.authentication.login.LoginViewModel
import com.atom.android.lebo.ui.authentication.loginOTP.LoginOTPViewModel
import com.atom.android.lebo.ui.cart.CartViewModel
import com.atom.android.lebo.ui.changepassword.ChangedPasswordViewModel
import com.atom.android.lebo.ui.checkout.CheckoutViewModel
import com.atom.android.lebo.ui.detail.DetailViewModel
import com.atom.android.lebo.ui.favorite.FavoriteViewModel
import com.atom.android.lebo.ui.home.HomeViewModel
//import com.atom.android.lebo.ui.library.LibraryViewModel
import com.atom.android.lebo.ui.main.MainViewModel
import com.atom.android.lebo.ui.notification.NotificationViewModel
import com.atom.android.lebo.ui.order.OrderDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { LoginOTPViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
   // viewModel { LibraryViewModel() }
    viewModel { AccountViewModel(get(), get()) }
    viewModel { ChangedPasswordViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { CartViewModel(get(), get()) }
    viewModel { CheckoutViewModel(get(), get(), get()) }
    viewModel { NotificationViewModel(get()) }
    viewModel { OrderDetailViewModel(get()) }
}
