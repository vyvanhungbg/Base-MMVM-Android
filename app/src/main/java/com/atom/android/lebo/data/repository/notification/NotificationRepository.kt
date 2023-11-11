package com.atom.android.lebo.data.repository.notification

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.datasource.notification.INotificationDataSource
import com.atom.android.lebo.model.Notification
import io.reactivex.rxjava3.core.Single

class NotificationRepository(private val remote: INotificationDataSource.Remote) :
    INotificationRepository {

    override fun getNotification(): Single<BaseResponse<List<Notification>?>> {
        return remote.getNotification()
    }

    override fun updateNotification(id: Int): Single<BaseResponse<String?>> {
        return remote.updateNotification(id)
    }
}
