package com.tieuvy.android.base_mvvm_android_2.presentation.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tieuvy.android.base.BaseViewModel
import com.tieuvy.android.base.extension.isNetworkAvailable
import com.tieuvy.android.base_mvvm_android_2.data.model.BaseResponseGitHub
import com.tieuvy.android.base_mvvm_android_2.data.model.SearchCacheLocal
import com.tieuvy.android.base_mvvm_android_2.data.model.User
import com.tieuvy.android.base_mvvm_android_2.data.use_case.SaveResultOfSearchByNameUserCase
import com.tieuvy.android.base_mvvm_android_2.data.use_case.SearchUserByNameLocalUserCase
import com.tieuvy.android.base_mvvm_android_2.data.use_case.SearchUserByNameOnlineUserCase

private const val TAG = "HomeViewModel"

class HomeViewModel(
    private val searchUserCaseByNameOnline: SearchUserByNameOnlineUserCase,
    private val searchUserCaseByNameLocal: SearchUserByNameLocalUserCase,
    private val saveSearchResultUseCase: SaveResultOfSearchByNameUserCase
) : BaseViewModel() {

    val textSearch = MutableLiveData<String>()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun searchUsersByName(context: Context, name: String) {
        _users.value = emptyList()
        if (context.isNetworkAvailable()) {
            searchUsersByNameOnline(name)
        } else {
            searchUsersByNameLocal(name)
        }
    }

    private fun saveSearchResultOnline(keySearch: String, data: BaseResponseGitHub<User>?) {
        executeTaskFlow(
            request = {
                saveSearchResultUseCase.execute(
                    SearchCacheLocal(
                        keySearch = keySearch,
                        data = data
                    )
                )
            },
            onSuccess = {
                Log.e(TAG, "saveSearchResultOnline: success ${it}")
            },
            onError = {
                Log.e(TAG, "saveSearchResultOnline: ${it.message}")
            }, showLoading = false
        )
    }

    private fun searchUsersByNameOnline(name: String) {
        executeTaskFlow(
            request = { searchUserCaseByNameOnline.execute(name) },
            onSuccess = {
                _users.value = it.items ?: emptyList()
                saveSearchResultOnline(keySearch = name, data = it)
            },
            onError = {
                it.printStackTrace()
                Log.e(TAG, "searchUsersByName: ${it.message}")
                // try search in local when server error
                searchUsersByNameLocal(name)
            }
        )
    }

    private fun searchUsersByNameLocal(name: String) {
        executeTaskFlow(
            request = { searchUserCaseByNameLocal.execute(name) },
            onSuccess = {
                _users.value = it.items ?: emptyList()
            },
            onError = {
                it.printStackTrace()
                Log.e(TAG, "searchUsersByNameLocal: ${it.message}")
            }
        )
    }
}