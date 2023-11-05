package com.hungvv.base_mvvm_android_3.data.repositories.user

import com.hungvv.base_mvvm_android_3.data.data_source.user.UserDataSource
import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.di.mapError
import com.tieuvy.android.base.BaseRepository
import com.tieuvy.android.base.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val remote: UserDataSource.Remote,
    private val local: UserDataSource.Local
) : UserRepository,
    BaseRepository() {

    override suspend fun searchUserByName(name: String): Flow<DataState<BaseResponseGitHub<User>?>> =
        flow {
            emit(DataState.Loading)
            emit(getResult(transformError = ::mapError) { remote.searchUserByName(name) })
        }.flowOn(Dispatchers.IO).catch {
            emit(DataState.Error(Exception(it.message)))
        }

    override suspend fun searchUserByNameLocal(name: String): Flow<DataState<BaseResponseGitHub<User>>> =
        flow {
            emit(DataState.Loading)
            val result = getResult(transformError = ::mapError) { local.searchUserByName(name) }
            if (result is DataState.Success && result.data != null) {
                emit(DataState.Success(result.data!!))
            } else {
                emit(DataState.Error(Exception("Can not find value with search key ${name}")))
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(DataState.Error(Exception(it.message)))
        }

    override suspend fun saveSearchResult(searchResult: SearchCacheLocal): Flow<DataState<Long>> =
        flow {
            emit(DataState.Loading)
            val result =
                getResult(transformError = ::mapError) { local.saveSearchResult(searchResult) }
            emit(result)
        }.flowOn(Dispatchers.IO).catch {
            emit(DataState.Error(Exception(it.message)))
        }

}