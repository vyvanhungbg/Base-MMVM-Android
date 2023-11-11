package com.atom.android.lebo.data.repository.login

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.datasource.login.ILoginDataSource
import com.atom.android.lebo.model.LoginEntity
import io.reactivex.rxjava3.core.Single

class LoginRepository(
    private val remote: ILoginDataSource.Remote,
) : ILoginRepository {

    override fun login(loginEntity: LoginEntity): Single<BaseResponse<String?>> {
        return remote.login(loginEntity)
    }

    override fun requestOTPLogin(email: String?): Single<BaseResponse<String?>> {
        return remote.requestOTPLogin(email)
    }

    override fun checkLogin(): Single<BaseResponse<String?>> {
        return remote.checkLogin()
    }
}
