package com.tieuvy.android.base_mvvm_android_2.data.use_case

import com.tieuvy.android.base.BaseUseCase
import com.tieuvy.android.base.DataState
import com.tieuvy.android.base_mvvm_android_2.data.model.BaseResponseGitHub
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import com.tieuvy.android.base_mvvm_android_2.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class SearchUserByNameOnlineUserCase(private val repo: UserRepository): BaseUseCase<String, Flow<DataState<BaseResponseGitHub<User>>>>() {

    override suspend fun execute(request: String): Flow<DataState<BaseResponseGitHub<User>>> {
        return  repo.searchUserByName(request)
    }

}