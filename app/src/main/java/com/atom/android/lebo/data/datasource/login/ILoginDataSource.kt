package com.atom.android.lebo.data.datasource.login

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.LoginEntity
import io.reactivex.rxjava3.core.Single

interface ILoginDataSource {

    interface Remote {
        fun login(loginEntity: LoginEntity): Single<BaseResponse<String?>>
        fun requestOTPLogin(email: String?): Single<BaseResponse<String?>>
        fun checkLogin(): Single<BaseResponse<String?>>
    }
}
