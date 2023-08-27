package com.tieuvy.android.base_mvvm_android_2.data.data_source.user

import com.tieuvy.android.base_mvvm_android_2.data.model.BaseResponseGitHub
import com.tieuvy.android.base_mvvm_android_2.data.model.SearchCacheLocal
import com.tieuvy.android.base_mvvm_android_2.data.model.User

interface UserDataSource {
    interface Remote {
        suspend fun searchUserByName(name: String): BaseResponseGitHub<User>
    }

    interface Local {
        suspend fun searchUserByName(name: String): BaseResponseGitHub<User>?
        suspend fun saveSearchResult(searchResult: SearchCacheLocal):Long
    }
}