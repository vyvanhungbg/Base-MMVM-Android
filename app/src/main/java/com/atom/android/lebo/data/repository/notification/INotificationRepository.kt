package com.atom.android.lebo.data.repository.notification

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Notification
import io.reactivex.rxjava3.core.Single

interface INotificationRepository {

    fun getNotification(): Single<BaseResponse<List<Notification>?>>
    fun updateNotification(id: Int): Single<BaseResponse<String?>>
}
