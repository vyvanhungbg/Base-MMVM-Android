package com.atom.android.lebo.data.repository.forgotpassword

import com.atom.android.lebo.base.BaseResponse
import io.reactivex.rxjava3.core.Single

interface IForgotPasswordRepository {

    fun requestForgotPassword(email: String): Single<BaseResponse<String?>>
}
