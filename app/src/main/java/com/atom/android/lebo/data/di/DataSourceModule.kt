package com.atom.android.lebo.data.di

import com.atom.android.lebo.data.datasource.author.AuthorRemoteDataSource
import com.atom.android.lebo.data.datasource.author.IAuthorDataSource
import com.atom.android.lebo.data.datasource.bill.BillRemoteDataSource
import com.atom.android.lebo.data.datasource.bill.IBillDataSource
import com.atom.android.lebo.data.datasource.book.BookRemoteDataSource
import com.atom.android.lebo.data.datasource.book.IBookDataSource
import com.atom.android.lebo.data.datasource.forgotpassword.ForgotPasswordRemoteDataSource
import com.atom.android.lebo.data.datasource.forgotpassword.IForgotPasswordDataSource
import com.atom.android.lebo.data.datasource.login.ILoginDataSource
import com.atom.android.lebo.data.datasource.login.LoginRemoteDataSource
import com.atom.android.lebo.data.datasource.notification.INotificationDataSource
import com.atom.android.lebo.data.datasource.notification.NotificationRemoteDataSource
import com.atom.android.lebo.data.datasource.ship.IShipDataSource
import com.atom.android.lebo.data.datasource.ship.ShipRemoteDataSource
import com.atom.android.lebo.data.datasource.user.IUserDataSource
import com.atom.android.lebo.data.datasource.user.UserRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<ILoginDataSource.Remote> { LoginRemoteDataSource(get()) }
    single<IForgotPasswordDataSource.Remote> { ForgotPasswordRemoteDataSource(get()) }
    single<IUserDataSource.Remote> { UserRemoteDataSource(get()) }
    single<IBookDataSource.Remote> { BookRemoteDataSource(get()) }
    single<IAuthorDataSource.Remote> { AuthorRemoteDataSource(get()) }
    single<IBillDataSource.Remote> { BillRemoteDataSource(get()) }
    single<IShipDataSource.Remote> { ShipRemoteDataSource(get()) }
    single<INotificationDataSource.Remote> { NotificationRemoteDataSource(get()) }
}
