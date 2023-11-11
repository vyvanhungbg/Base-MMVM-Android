package com.atom.android.lebo.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.repository.notification.NotificationRepository
import com.atom.android.lebo.model.Notification
import com.atom.android.lebo.utils.constants.Constant

class NotificationViewModel(private val repository: NotificationRepository) : BaseViewModel() {

    private val _notification = MutableLiveData<List<Notification>?>()
    val notification: LiveData<List<Notification>?>
        get() = _notification

    private val _swipeRefreshState = MutableLiveData<Boolean>()
    val swipeRefreshState: LiveData<Boolean> = _swipeRefreshState

    init {
        getAllNotification()
    }

    fun getAllNotification() {
        registerDisposable(
            executeTask(
                task = { repository.getNotification() },
                onSuccess = {
                    _notification.value = it.data
                    _swipeRefreshState.value = false
                },
                onError = {
                    error.value = it.message
                    _swipeRefreshState.value = false
                }
            )
        )
    }

    fun updateNotification(id: Int) {
        registerDisposable(
            executeTask(
                task = { repository.updateNotification(id) },
                onSuccess = {
                    updateList(id)
                },
                onError = {},
                loadingInvisible = false
            ),
        )
    }

    private fun updateList(id: Int) {
        val oldList = notification.value?.toMutableList()
        val index = oldList?.indexOfFirst { item -> item.id == id }
        if (index != null && index != Constant.DEFAULT.ERROR_INDEX) {
            val newItem = oldList[index].copy(isRead = true)
            oldList[index] = newItem
            _notification.value = oldList
        }
    }
}
