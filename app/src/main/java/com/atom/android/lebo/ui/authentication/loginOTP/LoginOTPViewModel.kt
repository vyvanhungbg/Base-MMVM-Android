package com.atom.android.lebo.ui.authentication.loginOTP

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.repository.login.LoginRepository
import com.atom.android.lebo.model.LoginEntity
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.ignoreFastAction
import com.atom.android.lebo.utils.extensions.isValidEmail
import com.atom.android.lebo.utils.extensions.saveTokenLogin
import com.atom.android.lebo.utils.extensions.withIOToMainThread
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class LoginOTPViewModel(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val _loginState = MutableLiveData<String>()
    val loginState: LiveData<String>
        get() = _loginState

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _countDown = MutableLiveData<String>()
    val countDown: LiveData<String>
        get() = _countDown

    private val _finishCountDown = MutableLiveData<Boolean>()
    val finishCountDown: LiveData<Boolean>
        get() = _finishCountDown

    private val _autoSubmitOTP = MutableLiveData<String>()
    val autoSubmitOTP: LiveData<String>
        get() = _autoSubmitOTP

    private val countDownObservable =
        Observable.interval(Constant.SECOND_TO_MILLI, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil { it == TIME_OTP }
            .map { it -> (TIME_OTP - it.inc()).toString() }
            .doOnComplete {
                _finishCountDown.value = true
            }

    fun login(context: Context?, email: String?, password: String) {
        val validateEmailResult = email.isValidEmail(context)
        if (email.isNullOrEmpty() || validateEmailResult.first.not()) {
            error.value = validateEmailResult.second
            return
        }
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

    fun requestOTP(email: String?) {
        registerDisposable(
            executeTask(
                task = { loginRepository.requestOTPLogin(email) },
                onSuccess = {
                    countDown()
                    if (it.status) {
                        _message.value = it.message
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

    private fun countDown() {
        _countDown.value = TIME_OTP.toString()
        registerDisposable(
            countDownObservable.subscribe(
                { _countDown.value = it },
                { error.value = it.toString() }
            )
        )
    }

    fun autoSubmitOTP(textOTPObservable: Observable<CharSequence>) {
        registerDisposable(
            textOTPObservable
                .ignoreFastAction()
                .withIOToMainThread()
                .subscribe({ _autoSubmitOTP.value = it.toString() }, { error.value = it.message })
        )
    }

    companion object {
        const val TIME_OTP = 90L
    }
}
