package com.hungvv.base_mvvm_android_3.use_case.search

import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.data.repositories.user.UserRepository
import com.tieuvy.android.base.BaseUseCase
import com.tieuvy.android.base.DataState
import kotlinx.coroutines.flow.Flow

class SearchUserByNameLocalUseCase(private val repo: UserRepository): BaseUseCase<String, Flow<DataState<BaseResponseGitHub<User>>>>() {

    override suspend fun execute(request: String): Flow<DataState<BaseResponseGitHub<User>>> {
        return  repo.searchUserByNameLocal(request)
    }

}