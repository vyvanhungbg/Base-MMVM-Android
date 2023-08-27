package com.tieuvy.android.base_mvvm_android_2.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tieuvy.android.base_mvvm_android_2.data.storage.local.database.AppDatabase
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Entity(tableName = AppDatabase.Companion.TABLES.USER)
@Serializable
data class User(
    @PrimaryKey
    val id: Long,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("url")
    val accountUrl: String,
    @SerialName("repos_url")
    val repoUrl: String,
    @SerialName("following_url")
    val followingUrl: String,
    @SerialName("followers_url")
    val followersUrl: String,
) : Parcelable