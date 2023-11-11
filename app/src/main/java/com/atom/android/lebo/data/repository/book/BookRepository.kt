package com.atom.android.lebo.data.repository.book

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.datasource.book.IBookDataSource
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.model.Genre
import com.atom.android.lebo.model.Slider
import io.reactivex.rxjava3.core.Single

class BookRepository(private val remote: IBookDataSource.Remote) : IBookRepository {

    override fun getGenre(): Single<BaseResponse<List<Genre>?>> = remote.getGenre()

    override fun getBook(page: Int): Single<BaseResponse<List<Book>?>> = remote.getBook(page)

    override fun getSlider(): Single<BaseResponse<List<Slider>?>> = remote.getSlider()

    override fun getBookByID(id: Int): Single<BaseResponse<Book?>> = remote.getBookByID(id)

    override fun getBookRelate(idBook: Int): Single<BaseResponse<List<Book>?>> =
        remote.getRelate(idBook)

    override fun getBooksByID(id: List<Int>): Single<BaseResponse<List<Book>?>> =
        remote.getBooksByID(id)
}
