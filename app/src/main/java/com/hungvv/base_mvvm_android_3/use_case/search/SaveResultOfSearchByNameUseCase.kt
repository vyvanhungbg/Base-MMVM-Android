package com.hungvv.base_mvvm_android_3.use_case.search

import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import com.hungvv.base_mvvm_android_3.data.repositories.user.UserRepository
import com.tieuvy.android.base.BaseUseCase
import com.tieuvy.android.base.DataState
import kotlinx.coroutines.flow.Flow

class SaveResultOfSearchByNameUseCase(private val repo: UserRepository) :
    BaseUseCase<SearchCacheLocal, Flow<DataState<Long>>>() {

    override suspend fun execute(request: SearchCacheLocal): Flow<DataState<Long>> {
        return repo.saveSearchResult(request)
    }


}