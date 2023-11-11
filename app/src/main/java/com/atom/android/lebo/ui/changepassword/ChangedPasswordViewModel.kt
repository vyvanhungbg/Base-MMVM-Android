package com.atom.android.lebo.ui.changepassword

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.api.service.AuthApiService
import com.atom.android.lebo.utils.extensions.isValidConfirmPassword
import com.atom.android.lebo.utils.extensions.isValidPassword
import io.reactivex.rxjava3.core.Observable

class ChangedPasswordViewModel(private val userService: AuthApiService.UserService) :
    BaseViewModel() {

    private val _changedPasswordState = MutableLiveData<String>()
    val changedPasswordState: LiveData<String>
        get() = _changedPasswordState

    private val _oldPasswordValid = MutableLiveData<Pair<Boolean, String?>>()
    val oldPasswordValid: LiveData<Pair<Boolean, String?>>
        get() = _oldPasswordValid

    private val _newPasswordValid = MutableLiveData<Pair<Boolean, String?>>()
    val newPasswordValid: LiveData<Pair<Boolean, String?>>
        get() = _newPasswordValid

    private val _confirmPasswordValid = MutableLiveData<Pair<Boolean, String?>>()
    val confirmPasswordValid: LiveData<Pair<Boolean, String?>>
        get() = _confirmPasswordValid

    private val _validateForm = MutableLiveData<Boolean>()
    val validateForm: LiveData<Boolean>
        get() = _validateForm

    fun changedPassword(oldPassword: String, newPassword: String) {
        registerDisposable(
            executeTask(
                task = { userService.changePassword(oldPassword, newPassword) },
                onSuccess = {
                    if (it.status) {
                        _changedPasswordState.value = it.message
                    } else {
                        error.value = it.message
                    }
                },
                onError = { error.value = it.message }
            )
        )
    }

    fun validatePassword(
        context: Context?,
        oldPassword: Observable<CharSequence>?,
        newPassword: Observable<CharSequence>?,
        confirmPassword: Observable<CharSequence>?
    ) {
        registerDisposable(
            Observable.combineLatest(
                oldPassword,
                newPassword,
                confirmPassword
            ) { _oldPassword, _newPassword, _confirmPassword ->
                val validOldPasswordResult = _oldPassword.toString().isValidPassword(context)
                val validNewPasswordResult = _newPassword.toString().isValidPassword(context)
                val validConfirmPasswordResult =
                    _confirmPassword.toString()
                        .isValidConfirmPassword(context, _newPassword.toString())
                _oldPasswordValid.value = validOldPasswordResult
                _newPasswordValid.value = validNewPasswordResult
                _confirmPasswordValid.value = validConfirmPasswordResult
                validOldPasswordResult.first && validNewPasswordResult.first && validConfirmPasswordResult.first
            }.distinctUntilChanged().subscribe { _validateForm.value = it }
        )
    }
}
