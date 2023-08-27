package com.tieuvy.android.base_mvvm_android_2.data.storage.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import com.tieuvy.android.base_mvvm_android_2.data.storage.local.database.AppDatabase

@Dao
interface UserDao {

    @Query("SELECT * FROM ${AppDatabase.Companion.TABLES.USER} WHERE id =:id")
    suspend fun getUserById(id: Long): User?
}