package com.atom.android.lebo.data.datasource.forgotpassword

import com.atom.android.lebo.base.BaseResponse
import io.reactivex.rxjava3.core.Single

interface IForgotPasswordDataSource {

    interface Remote {
        fun requestForgotPassword(email: String): Single<BaseResponse<String?>>
    }
}
