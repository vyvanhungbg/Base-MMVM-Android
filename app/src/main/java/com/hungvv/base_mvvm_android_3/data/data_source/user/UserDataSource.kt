package com.hungvv.base_mvvm_android_3.data.data_source.user

import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import com.hungvv.base_mvvm_android_3.data.model.User
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

interface UserDataSource {
    interface Remote {
        suspend fun searchUserByName(name: String): BaseResponseGitHub<User>?
    }

    interface Local {
        suspend fun searchUserByName(name: String): BaseResponseGitHub<User>?
        suspend fun saveSearchResult(searchResult: SearchCacheLocal):Long
    }
}