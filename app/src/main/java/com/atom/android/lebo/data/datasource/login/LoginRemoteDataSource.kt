package com.atom.android.lebo.data.datasource.login

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.UnAuthApiService
import com.atom.android.lebo.model.LoginEntity
import io.reactivex.rxjava3.core.Single

class LoginRemoteDataSource(private val loginService: UnAuthApiService.LoginService) :
    ILoginDataSource.Remote {

    override fun login(loginEntity: LoginEntity): Single<BaseResponse<String?>> {
        return loginService.login(loginEntity)
    }

    override fun requestOTPLogin(email: String?): Single<BaseResponse<String?>> {
        return loginService.requestOTPLogin(email)
    }

    override fun checkLogin(): Single<BaseResponse<String?>> {
        return loginService.checkLogin()
    }
}
