package com.hungvv.base_mvvm_android_3.presentation.home_epoxy

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungvv.base_mvvm_android_3.R
import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.SearchCacheLocal
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.use_case.search.SaveResultOfSearchByNameUseCase
import com.hungvv.base_mvvm_android_3.use_case.search.SearchUserByNameLocalUseCase
import com.hungvv.base_mvvm_android_3.use_case.search.SearchUserByNameOnlineUseCase
import com.hungvv.base_mvvm_android_3.utils.util.getKeyLastSearch
import com.hungvv.base_mvvm_android_3.utils.util.setKeyLastSearch
import com.tencent.mmkv.MMKV
import com.tieuvy.android.base.BaseViewModel
import com.tieuvy.android.base.extension.isNetworkAvailable
import com.tieuvy.android.base.extension.showToast
import timber.log.Timber

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

private const val TAG = "HomeEpoxyViewModel"

class HomeEpoxyViewModel(
    private val searchUserByNameOnlineUseCase: SearchUserByNameOnlineUseCase,
    private val searchUserCaseByNameLocalUseCase: SearchUserByNameLocalUseCase,
    private val saveSearchResultUseCase: SaveResultOfSearchByNameUseCase,
    private val mmkv: MMKV,
) : BaseViewModel() {

    private val defaultSearch = "vyvanhungbg"

    val textSearch = MutableLiveData<String>(getLastKeySearch() ?: defaultSearch)

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun initSearchDefault(context: Context){
        searchUsersByName(context)
    }

    fun searchUsersByName(context: Context) {
        textSearch.value?.let {_searchKeys->
            if (_searchKeys.isEmpty()) {
                context.showToast(context.getString(R.string.please_enter_name))
            } else {
                saveLastKeySearch(_searchKeys)
                if (context.isNetworkAvailable()) {
                    searchUsersByNameOnline(_searchKeys)
                } else {
                    searchUsersByNameLocal(_searchKeys)
                }
            }
        }

    }

    private fun getLastKeySearch() = mmkv.getKeyLastSearch()

    private fun saveLastKeySearch(keySearch: String) = mmkv.setKeyLastSearch(keySearch)

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
                Timber.e(" success : ${it}")
            },
            onError = {
                Timber.e(" error: ${it.message}")
                _users.value = emptyList()
            }, showLoading = false
        )
    }

    private fun searchUsersByNameOnline(name: String) {
        executeTaskFlow(
            request = { searchUserByNameOnlineUseCase.execute(name) },
            onSuccess = {
                _users.value = it?.items ?: emptyList()
                saveSearchResultOnline(keySearch = name, data = it)
            },
            onError = {
                it.printStackTrace()
                Timber.e("error: ${it.message}")
                // try search in local when server error
                searchUsersByNameLocal(name)
            }
        )
    }

    private fun searchUsersByNameLocal(name: String) {
        executeTaskFlow(
            request = { searchUserCaseByNameLocalUseCase.execute(name) },
            onSuccess = {
                _users.value = it.items ?: emptyList()
            },
            onError = {
                it.printStackTrace()
                Timber.e( "error: ${it.message}")
                _users.value = emptyList()
            }
        )
    }

}

