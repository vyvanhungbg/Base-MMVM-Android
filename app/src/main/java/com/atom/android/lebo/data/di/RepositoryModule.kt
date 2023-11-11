package com.atom.android.lebo.data.di

import com.atom.android.lebo.data.repository.author.AuthorRepository
import com.atom.android.lebo.data.repository.bill.BillRepository
import com.atom.android.lebo.data.repository.book.BookRepository
import com.atom.android.lebo.data.repository.forgotpassword.ForgotPasswordRepository
import com.atom.android.lebo.data.repository.login.LoginRepository
import com.atom.android.lebo.data.repository.notification.NotificationRepository
import com.atom.android.lebo.data.repository.ship.ShipRepository
import com.atom.android.lebo.data.repository.user.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
    single { ForgotPasswordRepository(get()) }
    single { UserRepository(get()) }
    single { BookRepository(get()) }
    single { AuthorRepository(get()) }
    single { BillRepository(get()) }
    single { ShipRepository(get()) }
    single { NotificationRepository(get()) }
}
