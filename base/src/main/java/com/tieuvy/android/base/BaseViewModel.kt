package com.tieuvy.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val loading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = loading

    private val error = SingleLiveEvent<String>()
    val hasError: LiveData<String>
        get() = error


    protected fun <T> executeTask(
        request: suspend CoroutineScope.() -> DataState<T>,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {},
        showLoading: Boolean = true
    ) {
        viewModelScope.launch {
            when (val response = request(this)) {
                is DataState.Loading -> {
                    if (showLoading) showLoading()

                }
                is DataState.Success -> {
                    onSuccess(response.data)
                    hideLoading()
                }
                is DataState.Error -> {
                    onError(response.exception)
                    hideLoading()
                }
            }
        }
    }

    protected fun <T> executeTaskFlow(
        request: suspend CoroutineScope.() -> Flow<DataState<T>>,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {},
        showLoading: Boolean = true
    ) {
        viewModelScope.launch {
            request(this).collect {
                when (it) {
                    is DataState.Success -> {
                        hideLoading()
                        onSuccess(it.data)
                    }

                    is DataState.Loading -> {
                        if (showLoading) showLoading()
                    }

                    is DataState.Error -> {
                        hideLoading()
                        onError(it.exception)
                        error.value = it.exception.message
                    }
                }
            }

        }
    }

    private fun showLoading() {
        loading.value = true
    }

    fun hideLoading() {
        loading.value = false
    }

}
