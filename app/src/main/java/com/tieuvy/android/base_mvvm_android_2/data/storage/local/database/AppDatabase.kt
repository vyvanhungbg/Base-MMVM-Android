package com.tieuvy.android.base_mvvm_android_2.data.storage.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import com.tieuvy.android.base_mvvm_android_2.data.storage.local.database.AppDatabase.Companion.DB_VERSION
import com.tieuvy.android.base_mvvm_android_2.data.storage.local.database.dao.UserDao
import java.util.concurrent.Executors

private const val TAG = "AppDatabase"

@Database(entities = [User::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "AppDatabase"
        private const val ENABLE_LOG = true
        object TABLES{
            const val USER = "user"
        }

        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .setQueryCallback(
                    { a, b ->
                        if (ENABLE_LOG) {
                            Log.e(TAG, "QUERY [$a , ${b.toString()}] ")
                        }
                    },
                    Executors.newSingleThreadExecutor()
                ).build()
    }
}