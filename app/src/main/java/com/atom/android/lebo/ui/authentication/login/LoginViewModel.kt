package com.atom.android.lebo.ui.authentication.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.repository.login.LoginRepository
import com.atom.android.lebo.model.LoginEntity
import com.atom.android.lebo.utils.extensions.isValidEmail
import com.atom.android.lebo.utils.extensions.isValidPassword
import com.atom.android.lebo.utils.extensions.saveTokenLogin
import io.reactivex.rxjava3.core.Observable

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val _validateEmail = MutableLiveData<Pair<Boolean, String?>>()
    val validateEmail: LiveData<Pair<Boolean, String?>>
        get() = _validateEmail

    private val _validatePassword = MutableLiveData<Pair<Boolean, String?>>()
    val validatePassword: LiveData<Pair<Boolean, String?>>
        get() = _validatePassword

    private val _validateForm = MutableLiveData<Boolean>()
    val validateForm: LiveData<Boolean>
        get() = _validateForm

    private val _loginState = MutableLiveData<String>()
    val loginState: LiveData<String>
        get() = _loginState

    private val _account = MutableLiveData<LoginEntity>()
    val account: LiveData<LoginEntity>
        get() = _account

    fun setAccount(account: LoginEntity) {
        _account.value = account
    }

    fun login(email: String, password: String) {
        registerDisposable(
            executeTask(
                task = { loginRepository.login(LoginEntity(email, password)) },
                onSuccess = {
                    if (it.status) {
                        sharedPreferences.saveTokenLogin(it.data.toString())
                        _loginState.value = it.message
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

    fun validateForm(
        context: Context?,
        emailObservable: Observable<CharSequence>?,
        passwordObservable: Observable<CharSequence>?
    ) {
        registerDisposable(
            Observable.combineLatest(emailObservable, passwordObservable) { textEmail, textPass ->
                val validEmailResult = textEmail.toString().isValidEmail(context)
                val validPasswordResult = textPass.toString().isValidPassword(context)
                _validateEmail.value = validEmailResult
                _validatePassword.value = validPasswordResult
                validEmailResult.first && validPasswordResult.first
            }.distinctUntilChanged()
                .subscribe({ _validateForm.value = it }, { error.value = it.message })
        )
    }
}
