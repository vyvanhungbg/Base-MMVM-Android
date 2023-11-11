package com.atom.android.lebo.data.datasource.author

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.UnAuthApiService
import com.atom.android.lebo.model.Author
import io.reactivex.rxjava3.core.Single

class AuthorRemoteDataSource(private val service: UnAuthApiService.AuthorService) :
    IAuthorDataSource.Remote {

    override fun getAuthor(): Single<BaseResponse<List<Author>?>> {
        return service.getAuthor()
    }
}
