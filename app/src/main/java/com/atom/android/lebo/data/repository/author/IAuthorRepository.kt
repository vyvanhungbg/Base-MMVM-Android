package com.atom.android.lebo.data.repository.author

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Author
import io.reactivex.rxjava3.core.Single

interface IAuthorRepository {

    fun getAuthors(): Single<BaseResponse<List<Author>?>>
}
