package com.atom.android.lebo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.rxjava3.EmptyResultSetException
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.local.FavoriteEntityLocal
import com.atom.android.lebo.data.local.FavoriteEntityLocalDAO
import com.atom.android.lebo.data.repository.book.BookRepository
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.utils.constants.Constant

class DetailViewModel(
    private val bookRepository: BookRepository,
    private val favoriteEntityLocalDAO: FavoriteEntityLocalDAO
) : BaseViewModel() {

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?>
        get() = _book

    private val _favoriteItem = MutableLiveData<FavoriteEntityLocal?>()
    val favoriteItem: LiveData<FavoriteEntityLocal?>
        get() = _favoriteItem

    private val _bookRelative = MutableLiveData<List<Book>>()
    val bookRelative: LiveData<List<Book>>
        get() = _bookRelative

    fun getBook(id: Int) {
        registerDisposable(
            executeTask(
                task = { bookRepository.getBookByID(id) },
                onSuccess = {
                    if (it.status) {
                        _book.value = it.data
                    }
                },
                onError = { error.value = it.message }
            )
        )
    }

    fun setFavorite(itemBook: Book?) {
        itemBook?.let {
            val itemFavorite = _favoriteItem.value
            val favoriteLocal = FavoriteEntityLocal(it.id, it.image, it.title)
            if (itemFavorite == null) {
                registerDisposable(
                    executeTask(
                        task = { favoriteEntityLocalDAO.insert(favoriteLocal) },
                        onSuccess = {
                            if (it != Constant.DEFAULT.INSERT_FAILED) {
                                _favoriteItem.value = favoriteLocal
                            }
                        },
                        onError = { error.value = it.message }
                    )
                )
            } else {
                registerDisposable(
                    executeTask(
                        task = { favoriteEntityLocalDAO.delete(itemFavorite) },
                        onSuccess = {
                            if (it == Constant.DEFAULT.ROW_EFFECT) {
                                _favoriteItem.value = null
                            }
                        },
                        onError = { error.value = it.message }
                    )
                )
            }
        }
    }

    fun getFavoriteItem(bundleBook: String) {
        registerDisposable(
            executeTask(
                task = { favoriteEntityLocalDAO.findByID(bundleBook.toInt()) },
                onSuccess = {
                    _favoriteItem.value = it
                },
                onError = {
                    if (it is EmptyResultSetException) {
                        _favoriteItem.value = null
                    }
                }
            )
        )
    }

    fun getBookRelate(id: Int) {
        registerDisposable(
            executeTask(
                task = { bookRepository.getBookRelate(id) },
                onSuccess = {
                    if (it.status) {
                        _bookRelative.value = it.data as List<Book>
                    }
                },
                onError = {
                    error.value = it.message
                },
                loadingInvisible = false
            )
        )
    }
}
