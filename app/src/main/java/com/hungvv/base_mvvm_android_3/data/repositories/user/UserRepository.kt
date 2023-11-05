package com.hungvv.base_mvvm_android_3.data.repositories.user

import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import com.hungvv.base_mvvm_android_3.data.model.User
import com.tieuvy.android.base.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun searchUserByName(name: String): Flow<DataState<BaseResponseGitHub<User>?>>
    suspend fun searchUserByNameLocal(name: String): Flow<DataState<BaseResponseGitHub<User>>>
    suspend fun saveSearchResult(searchResult: SearchCacheLocal): Flow<DataState<Long>>

}