package com.hungvv.base_mvvm_android_3.data.data_source.user

import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.di.ApiService
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

class UserRemoteDataSource(private val apiService: ApiService): UserDataSource.Remote {

    override suspend fun searchUserByName(name: String): BaseResponseGitHub<User>? {
        return apiService.searchUserByKeyWord(keyWord = name)
    }
}