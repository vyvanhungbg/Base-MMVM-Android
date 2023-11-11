package com.atom.android.lebo.ui.account

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.repository.user.UserRepository
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.destroyTokenLogin

class AccountViewModel(
    private val sharedPreferences: SharedPreferences,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _logoutState = MutableLiveData<Boolean>()
    val logoutState: LiveData<Boolean>
        get() = _logoutState

    fun logout() {
        removeTokenNotification(Constant.DEFAULT.STRING)
    }

    private fun removeTokenNotification(token: String) {
        registerDisposable(
            executeTask(
                task = { userRepository.unregisterNotification(token) },
                onSuccess = { _logoutState.value = sharedPreferences.destroyTokenLogin() },
                onError = {},
                loadingInvisible = false
            )
        )
    }
}
