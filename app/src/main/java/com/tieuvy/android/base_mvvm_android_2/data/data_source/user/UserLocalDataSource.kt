package com.tieuvy.android.base_mvvm_android_2.data.data_source.user

import com.tieuvy.android.base_mvvm_android_2.data.model.BaseResponseGitHub
import com.tieuvy.android.base_mvvm_android_2.data.model.SearchCacheLocal
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import io.objectbox.Box

class UserLocalDataSource(private val boxSearchCacheLocal: Box<SearchCacheLocal>) :
    UserDataSource.Local {
    override suspend fun searchUserByName(name: String): BaseResponseGitHub<User>? {
        return boxSearchCacheLocal.query().filter { it -> it.keySearch == name }.build()
            .find().toMutableList().firstOrNull()?.data
    }

    override suspend fun saveSearchResult(searchResult: SearchCacheLocal): Long {
        val exits = boxSearchCacheLocal.query().filter { it -> it.keySearch == searchResult.keySearch }.build()
            .find().firstOrNull()
        if (exits != null) {
            boxSearchCacheLocal.remove(exits.id)
        }
       return boxSearchCacheLocal.put(searchResult)
    }


}