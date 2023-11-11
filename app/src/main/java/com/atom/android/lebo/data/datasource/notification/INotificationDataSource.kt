package com.atom.android.lebo.data.datasource.notification

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Notification
import io.reactivex.rxjava3.core.Single

interface INotificationDataSource {

    interface Remote {
        fun getNotification(): Single<BaseResponse<List<Notification>?>>
        fun updateNotification(id: Int): Single<BaseResponse<String?>>
    }
}
