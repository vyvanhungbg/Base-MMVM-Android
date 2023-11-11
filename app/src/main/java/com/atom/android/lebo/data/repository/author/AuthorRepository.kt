package com.atom.android.lebo.data.repository.author

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.datasource.author.IAuthorDataSource
import com.atom.android.lebo.model.Author
import io.reactivex.rxjava3.core.Single

class AuthorRepository(private val repository: IAuthorDataSource.Remote) : IAuthorRepository {

    override fun getAuthors(): Single<BaseResponse<List<Author>?>> {
        return repository.getAuthor()
    }
}
