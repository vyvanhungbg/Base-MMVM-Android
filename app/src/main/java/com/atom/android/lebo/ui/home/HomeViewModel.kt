package com.atom.android.lebo.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.local.CartEntityLocalDAO
import com.atom.android.lebo.data.repository.author.AuthorRepository
import com.atom.android.lebo.data.repository.book.BookRepository
import com.atom.android.lebo.model.Author
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.model.Genre
import com.atom.android.lebo.model.Slider
import com.atom.android.lebo.utils.constants.Constant
import io.reactivex.rxjava3.core.Single

class HomeViewModel(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val cartEntityLocalDAO: CartEntityLocalDAO
) : BaseViewModel() {

    private var currentPageBook = Constant.DEFAULT.FIRST_PAGE

    private val listBook = mutableListOf<Book>()

    private val _genre = MutableLiveData<List<Genre>?>()
    val genre: LiveData<List<Genre>?> = _genre

    private val _books = MutableLiveData<List<Book>?>()
    val books: LiveData<List<Book>?> = _books

    private val _slides = MutableLiveData<List<Slider>?>()
    val slides: LiveData<List<Slider>?> = _slides

    private val _author = MutableLiveData<List<Author>?>()
    val author: LiveData<List<Author>?> = _author

    private val _swipeRefreshState = MutableLiveData<Boolean>()
    val swipeRefreshState: LiveData<Boolean> = _swipeRefreshState

    private val _amountCart = MutableLiveData<Int>()
    val amountCart: LiveData<Int> = _amountCart

    fun getGenre() {
        registerDisposable(
            executeTask(
                task = { bookRepository.getGenre() },
                onSuccess = {
                    if (it.status) {
                        _genre.value = it.data
                    }
                },
                onError = {
                    error.value = it.message
                },
                loadingInvisible = false
            )
        )
    }

    fun getBook() {
        registerDisposable(
            executeTask(
                task = { bookRepository.getBook(currentPageBook++) },
                onSuccess = {
                    if (it.status) {
                        val books = it.data as List<Book>
                        if (books.isEmpty().not()) {
                            listBook.addAll(books)
                            _books.value = listBook
                        }
                    }
                },
                onError = {
                    error.value = it.message
                },
                loadingInvisible = false
            )
        )
    }

    fun getSlider() {
        registerDisposable(
            executeTask(
                task = { bookRepository.getSlider() },
                onSuccess = {
                    if (it.status) {
                        _slides.value = it.data
                    }
                },
                onError = {
                    error.value = it.message
                },
                loadingInvisible = false
            )
        )
    }

    fun getAuthor() {
        registerDisposable(
            executeTask(
                task = { authorRepository.getAuthors() },
                onSuccess = {
                    if (it.status) {
                        _author.value = it.data
                    }
                },
                onError = {
                    error.value = it.message
                },
                loadingInvisible = false
            )
        )
    }

    fun refreshData(context: Context?) {
        currentPageBook = Constant.DEFAULT.FIRST_PAGE
        registerDisposable(
            executeTask(
                task = {
                    Single.zip(
                        bookRepository.getBook(currentPageBook++),
                        bookRepository.getGenre(),
                        bookRepository.getSlider(),
                        authorRepository.getAuthors()
                    ) { _bookResponse, _genreResponse, _sliderResponse, _authorResponse ->
                        listBook.clear()
                        listBook.addAll(_bookResponse.data as List<Book>)
                        _books.postValue(listBook)
                        _genre.postValue(_genreResponse.data)
                        _slides.postValue(_sliderResponse.data)
                        _author.postValue(_authorResponse.data)
                        return@zip BaseResponse<Boolean>(
                            data = null,
                            status = _bookResponse.status && _genreResponse.status && _sliderResponse.status,
                            message = Constant.DEFAULT.STRING
                        )
                    }
                },
                onSuccess = { _swipeRefreshState.value = false },
                onError = {
                    _swipeRefreshState.value = false
                    error.value = context?.getString(R.string.mess_error_refresh_data)
                },
                loadingInvisible = false
            )
        )
    }

    fun getAmountCart() {
        registerDisposable(
            executeTask(
                task = { cartEntityLocalDAO.getTotalAmount() },
                onSuccess = {
                    _amountCart.value = it
                },
                onError = {
                    _amountCart.value = Constant.DEFAULT.ITEM_CART
                },
                loadingInvisible = false
            )
        )
    }
}
