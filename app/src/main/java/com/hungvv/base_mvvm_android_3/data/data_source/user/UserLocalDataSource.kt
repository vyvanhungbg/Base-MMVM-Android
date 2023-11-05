package com.hungvv.base_mvvm_android_3.data.data_source.user

import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import com.hungvv.base_mvvm_android_3.data.model.User
import io.objectbox.Box
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

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