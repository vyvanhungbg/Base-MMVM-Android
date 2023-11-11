package com.atom.android.lebo.data.datasource.author

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Author
import io.reactivex.rxjava3.core.Single

interface IAuthorDataSource {

    interface Remote {
        fun getAuthor(): Single<BaseResponse<List<Author>?>>
    }
}
