package com.atom.android.lebo.ui.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.local.CartEntityLocal
import com.atom.android.lebo.data.local.CartEntityLocalDAO
import com.atom.android.lebo.data.repository.book.BookRepository
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.model.OrderDetail
import com.atom.android.lebo.utils.constants.Constant


class CartViewModel(
    private val bookRepository: BookRepository,
    private val cartEntityLocalDAO: CartEntityLocalDAO
) : BaseViewModel() {

    private val _cart = MediatorLiveData<List<CartEntityLocal>?>()
    val cart: LiveData<List<CartEntityLocal>?>
        get() = _cart

    private val _books = MutableLiveData<List<Book>?>()
    val books: LiveData<List<Book>?>
        get() = _books

    private val _addToCartState = MutableLiveData<String>()
    val addToCartState: LiveData<String>
        get() = _addToCartState

    private val _totalPrice = MediatorLiveData<String>()
    val totalPrice: LiveData<String>
        get() = _totalPrice

    private val _isCheckedAll = MediatorLiveData<Boolean>()
    val isCheckedAll: LiveData<Boolean>
        get() = _isCheckedAll

    init {
        getBooks()
        _totalPrice.addSource(books) {
            calcTotalPrice()
        }
        _isCheckedAll.addSource(books) {
            val hasItemUnChecked = _books.value?.any { it.isChecked == false } ?: false
            _isCheckedAll.value = hasItemUnChecked.not()
        }
        _cart.addSource(_addToCartState) {
            getCartLocalBooks()
        }
    }

    fun getBooks() {
        registerDisposable(
            executeTask(
                task = {
                    cartEntityLocalDAO.getAll().flatMap { list ->
                        _cart.postValue(list)
                        return@flatMap bookRepository.getBooksByID(list.map { it.idBook })
                    }
                },
                onSuccess = {
                    val cart = _cart.value
                    _books.value = it.data?.map { book ->
                        val item = cart?.find { it.idBook == book.id }
                        return@map book.copy(
                            amount = item?.amount ?: Constant.DEFAULT.ITEM_CART,
                            isChecked = item?.isChecked ?: false
                        )
                    }
                },
                onError = { error.value = it.message }
            )
        )
    }

    private fun updateBookList(item: CartEntityLocal) {
        val oldList = _books.value?.toMutableList()
        val indexOldItem =
            oldList?.indexOfFirst { book -> book.id == item.idBook }
        indexOldItem?.let {
            if (it != -1) {
                val newItem = oldList[indexOldItem].copy(
                    amount = item.amount,
                    isChecked = item.isChecked
                )
                oldList[indexOldItem] = newItem
                _books.value = oldList
            }
        }
    }

    private fun deleteBookList(item: CartEntityLocal) {
        val oldList = _books.value
        _books.value =
            oldList?.filter { book -> book.id != item.idBook }
    }

    fun calcTotalPrice() {
        _totalPrice.value =
            _books.value?.filter { it.isChecked }?.sumOf { it.price * it.amount }?.toString()
                ?: Constant.DEFAULT.STRING
    }

    fun getListOrdersDetail(): List<OrderDetail> {
        return _books.value?.filter { it.isChecked }
            ?.map { it -> OrderDetail(it.id, it.amount, it.price) } ?: mutableListOf()
    }

    fun checkBoxAll(isChecked: Boolean) {
        val oldBooks = _books.value?.map { it.copy(isChecked = isChecked) }
        registerDisposable(
            executeTask(
                task = {
                    cartEntityLocalDAO.updateAllChecked(isChecked)
                },
                onSuccess = {
                    _books.value = oldBooks
                },
                onError = { _books.value = oldBooks }
            )
        )
    }

    fun getCartLocalBooks() {
        registerDisposable(
            executeTask(
                task = {
                    cartEntityLocalDAO.getAll()
                },
                onSuccess = {
                    _cart.value = it
                },
                onError = { _cart.value = mutableListOf() }
            )
        )
    }

    fun setItemInCart(
        context: Context?,
        item: CartEntityLocal,
        action: ACTION
    ) {
        registerDisposable(
            when (action) {
                ACTION.DELETE -> {
                    executeTask(
                        task = { cartEntityLocalDAO.delete(item) },
                        onSuccess = {
                            if (it == Constant.DEFAULT.ROW_EFFECT) {
                                _addToCartState.value =
                                    context?.getString(R.string.message_delete_success)
                                deleteBookList(item)
                            }
                        },
                        onError = { error.value = it.message }
                    )
                }
                ACTION.INSERT -> {
                    executeTask(
                        task = { cartEntityLocalDAO.insert(item) },
                        onSuccess = {
                            if (it != Constant.DEFAULT.INSERT_FAILED) {
                                _addToCartState.value =
                                    context?.getString(R.string.message_insert_success)
                            }
                        },
                        onError = { error.value = it.message }
                    )
                }
                else -> {
                    executeTask(
                        task = { cartEntityLocalDAO.update(item) },
                        onSuccess = {
                            if (it == Constant.DEFAULT.ROW_EFFECT) {

                                if (action == ACTION.ASC) {
                                    _addToCartState.value =
                                        context?.getString(R.string.message_insert_success)
                                } else {
                                    _addToCartState.value =
                                        context?.getString(R.string.message_remove_item_cart_success)
                                }
                                updateBookList(item)
                            }
                        },
                        onError = { error.value = it.message }
                    )
                }
            }
        )
    }
}

enum class ACTION(val value: Int) {
    DELETE(Constant.ACTION.DELETE),
    INSERT(Constant.ACTION.INSERT),
    DES(Constant.ACTION.DESCENDING),
    ASC(Constant.ACTION.ASCENDING),
}
