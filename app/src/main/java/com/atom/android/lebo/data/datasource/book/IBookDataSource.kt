package com.atom.android.lebo.data.datasource.book

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.model.Genre
import com.atom.android.lebo.model.Slider
import io.reactivex.rxjava3.core.Single

interface IBookDataSource {

    interface Remote {
        fun getGenre(): Single<BaseResponse<List<Genre>?>>
        fun getBook(page: Int): Single<BaseResponse<List<Book>?>>
        fun getRelate(idBook: Int): Single<BaseResponse<List<Book>?>>
        fun getSlider(): Single<BaseResponse<List<Slider>?>>
        fun getBookByID(id: Int): Single<BaseResponse<Book?>>
        fun getBooksByID(id: List<Int>): Single<BaseResponse<List<Book>?>>
    }
}
