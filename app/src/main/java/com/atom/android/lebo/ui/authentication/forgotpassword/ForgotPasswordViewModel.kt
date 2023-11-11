package com.atom.android.lebo.ui.authentication.forgotpassword

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.repository.forgotpassword.ForgotPasswordRepository
import com.atom.android.lebo.utils.extensions.isValidEmail
import io.reactivex.rxjava3.core.Observable

class ForgotPasswordViewModel(
    private val repository: ForgotPasswordRepository
) : BaseViewModel() {

    private val _validateEmail = MutableLiveData<Pair<Boolean, String?>>()
    val validateEmail: LiveData<Pair<Boolean, String?>>
        get() = _validateEmail

    private val _requestSuccess = MutableLiveData<String>()
    val requestSuccess: LiveData<String>
        get() = _requestSuccess

    fun requestForgotPassword(email: String) {
        registerDisposable(
            executeTask(
                task = { repository.requestForgotPassword(email) },
                onSuccess = {
                    if (it.status) {
                        _requestSuccess.value = it.message
                    } else {
                        error.value = it.message
                    }
                },
                onError = {
                    error.value = it.toString()
                }
            )
        )
    }

    fun validateEmail(context: Context?, emailObservable: Observable<CharSequence>?) {
        emailObservable?.let {
            registerDisposable(
                emailObservable
                    .map {
                        it.toString().isValidEmail(context)
                    }.subscribe({ _validateEmail.value = it }, { error.value = it.message })
            )
        }
    }
}
