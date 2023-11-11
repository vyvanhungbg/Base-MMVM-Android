package com.atom.android.lebo.data.repository.forgotpassword

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.datasource.forgotpassword.IForgotPasswordDataSource
import io.reactivex.rxjava3.core.Single

class ForgotPasswordRepository(private val remote: IForgotPasswordDataSource.Remote) :
    IForgotPasswordRepository {

    override fun requestForgotPassword(email: String): Single<BaseResponse<String?>> {
        return remote.requestForgotPassword(email)
    }
}
