package com.atom.android.lebo.data.datasource.user

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.AuthApiService
import com.atom.android.lebo.model.User
import io.reactivex.rxjava3.core.Single

class UserRemoteDataSource(private val userService: AuthApiService.UserService) :
    IUserDataSource.Remote {

    override fun getUsers(): Single<BaseResponse<User?>> {
        return userService.getUser()
    }

    override fun changedPassword(
        oldPassword: String,
        newPassword: String
    ): Single<BaseResponse<String?>> {
        return userService.changePassword(oldPassword, newPassword)
    }

    override fun registerNotification(token: String): Single<BaseResponse<String?>> {
        return userService.registerNotification(token)
    }

    override fun unregisterNotification(token: String): Single<BaseResponse<String?>> {
        return userService.registerNotification(token)
    }
}
