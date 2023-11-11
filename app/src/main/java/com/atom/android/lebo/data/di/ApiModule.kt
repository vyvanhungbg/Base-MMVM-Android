package com.atom.android.lebo.data.di

import com.atom.android.lebo.data.api.service.AuthApiService
import com.atom.android.lebo.data.api.service.UnAuthApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(UnAuthApiService.LoginService::class.java) }
    single { get<Retrofit>().create(UnAuthApiService.ForgotPasswordService::class.java) }
    single { get<Retrofit>().create(AuthApiService.UserService::class.java) }
    single { get<Retrofit>().create(UnAuthApiService.BookService::class.java) }
    single { get<Retrofit>().create(UnAuthApiService.AuthorService::class.java) }
    single { get<Retrofit>().create(UnAuthApiService.ShippingMethodService::class.java) }
    single { get<Retrofit>().create(AuthApiService.BillService::class.java) }
    single { get<Retrofit>().create(AuthApiService.NotificationService::class.java) }
}
