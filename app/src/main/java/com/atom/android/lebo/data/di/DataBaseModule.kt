package com.atom.android.lebo.data.di

import android.content.Context
import androidx.room.Room
import com.atom.android.lebo.data.local.DataBaseLocal
import org.koin.dsl.module

val databaseModule = module {
    single { provideLocalDatabase(get()) }
    single { provideCartDAO(get()) }
    single { provideFavoriteDAO(get()) }
}

private fun provideLocalDatabase(context: Context): DataBaseLocal {
    return Room.databaseBuilder(
        context.applicationContext,
        DataBaseLocal::class.java,
        DataBaseLocal.NAME
    ).build()
}

private fun provideCartDAO(localDatabase: DataBaseLocal) = localDatabase.cartDao
private fun provideFavoriteDAO(localDatabase: DataBaseLocal) = localDatabase.favoriteDao
