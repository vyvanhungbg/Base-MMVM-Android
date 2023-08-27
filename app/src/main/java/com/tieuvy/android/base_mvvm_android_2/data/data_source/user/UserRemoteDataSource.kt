package com.tieuvy.android.base_mvvm_android_2.data.data_source.user

import com.tieuvy.android.base_mvvm_android_2.data.model.BaseResponseGitHub
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client.end_point.EndpointSearch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class UserRemoteDataSource(private val client: HttpClient): UserDataSource.Remote {
    override suspend fun searchUserByName(name: String): BaseResponseGitHub<User> {
        //return client.get("https://api.github.com/search/users?q=${name}").body() as BaseResponseGitHub<NameUser>
        return client.get(EndpointSearch.UserName(name)).body() as BaseResponseGitHub<User>
    }
}