package com.atom.android.lebo.data.datasource.book

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.UnAuthApiService
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.model.Genre
import com.atom.android.lebo.model.Slider
import io.reactivex.rxjava3.core.Single

class BookRemoteDataSource(private val bookService: UnAuthApiService.BookService) :
    IBookDataSource.Remote {

    override fun getGenre(): Single<BaseResponse<List<Genre>?>> {
        return bookService.getGenre()
    }

    override fun getBook(page: Int): Single<BaseResponse<List<Book>?>> {
        return bookService.getBook(page)
    }

    override fun getRelate(idBook: Int): Single<BaseResponse<List<Book>?>> {
        return bookService.getBookRelate(idBook)
    }

    override fun getSlider(): Single<BaseResponse<List<Slider>?>> {
        return bookService.getSlider()
    }

    override fun getBookByID(id: Int): Single<BaseResponse<Book?>> {
        return bookService.getBookByID(id)
    }

    override fun getBooksByID(id: List<Int>): Single<BaseResponse<List<Book>?>> {
        return bookService.getBooksByID(id)
    }
}
