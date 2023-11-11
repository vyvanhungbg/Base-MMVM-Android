package com.atom.android.lebo.data.datasource.user

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.User
import io.reactivex.rxjava3.core.Single

interface IUserDataSource {

    interface Remote {
        fun getUsers(): Single<BaseResponse<User?>>
        fun changedPassword(oldPassword: String, newPassword: String): Single<BaseResponse<String?>>
        fun registerNotification(token: String): Single<BaseResponse<String?>>
        fun unregisterNotification(token: String): Single<BaseResponse<String?>>
    }
}
