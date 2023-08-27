package com.tieuvy.android.base_mvvm_android_2.data.use_case

import com.tieuvy.android.base.BaseUseCase
import com.tieuvy.android.base.DataState
import com.tieuvy.android.base_mvvm_android_2.data.model.SearchCacheLocal
import com.tieuvy.android.base_mvvm_android_2.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class SaveResultOfSearchByNameUserCase(private val repo: UserRepository) :
    BaseUseCase<SearchCacheLocal, Flow<DataState<Long>>>() {

    override suspend fun execute(request: SearchCacheLocal): Flow<DataState<Long>> {
        return repo.saveSearchResult(request)
    }


}