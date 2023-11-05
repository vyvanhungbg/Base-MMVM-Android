package com.hungvv.base_mvvm_android_3.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("url")
    val accountUrl: String,
    @SerializedName("repos_url")
    val repoUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
) : Parcelable{
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }
}