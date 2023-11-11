package com.atom.android.lebo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atom.android.lebo.utils.extensions.handleLoading
import com.atom.android.lebo.utils.extensions.withIOToMainThread
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {

    protected val loading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = loading

    protected val error = MutableLiveData<String>()
    val hasError: LiveData<String>
        get() = error

    private val compositeDisposable = CompositeDisposable()

    protected fun registerDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun <T> executeTask(
        task: () -> Single<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        loadingInvisible: Boolean = true
    ): Disposable {
        return if (loadingInvisible) {
            task().withIOToMainThread().handleLoading(::showLoading, ::hideLoading)
                .subscribe({ onSuccess(it) }, { onError(it) })
        } else {
            task().withIOToMainThread().subscribe({ onSuccess(it) }, { onError(it) })
        }
    }

    protected fun showLoading() {
        loading.value = true
    }

    protected fun hideLoading() {
        loading.value = false
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
