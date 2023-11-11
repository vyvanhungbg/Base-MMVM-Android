package com.atom.android.lebo.data.datasource.notification

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.AuthApiService
import com.atom.android.lebo.model.Notification
import io.reactivex.rxjava3.core.Single

class NotificationRemoteDataSource(private val service: AuthApiService.NotificationService) :
    INotificationDataSource.Remote {

    override fun getNotification(): Single<BaseResponse<List<Notification>?>> {
        return service.getNotification()
    }

    override fun updateNotification(id: Int): Single<BaseResponse<String?>> {
        return service.updateNotification(id)
    }
}
