package com.tieuvy.android.base_mvvm_android_2.data.repository.user

import com.tieuvy.android.base.DataState
import com.tieuvy.android.base_mvvm_android_2.data.model.BaseResponseGitHub
import com.tieuvy.android.base_mvvm_android_2.data.model.SearchCacheLocal
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun searchUserByName(name: String): Flow<DataState<BaseResponseGitHub<User>>>
    suspend fun searchUserByNameLocal(name: String): Flow<DataState<BaseResponseGitHub<User>>>
    suspend fun saveSearchResult(searchResult: SearchCacheLocal): Flow<DataState<Long>>

}