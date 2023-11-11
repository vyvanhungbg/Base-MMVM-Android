package com.atom.android.lebo.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.rxjava3.EmptyResultSetException
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.local.FavoriteEntityLocal
import com.atom.android.lebo.data.local.FavoriteEntityLocalDAO
import com.atom.android.lebo.utils.constants.Constant

class FavoriteViewModel(private val favoriteEntityLocalDAO: FavoriteEntityLocalDAO) :
    BaseViewModel() {

    private val _favoriteItem = MutableLiveData<List<FavoriteEntityLocal>>()
    val favoriteItem: LiveData<List<FavoriteEntityLocal>> = _favoriteItem

    fun getFavoriteItem() {
        registerDisposable(
            executeTask(
                task = { favoriteEntityLocalDAO.getAll() },
                onSuccess = {
                    _favoriteItem.value = it
                },
                onError = {
                    if (it is EmptyResultSetException) {
                        _favoriteItem.value = mutableListOf()
                    }
                }
            )
        )
    }

    fun deleteFavoriteItem(itemFavorite: FavoriteEntityLocal) {
        registerDisposable(
            executeTask(
                task = { favoriteEntityLocalDAO.delete(itemFavorite) },
                onSuccess = {
                    if (it == Constant.DEFAULT.ROW_EFFECT) {
                        val oldList = _favoriteItem.value?.toMutableList()
                        oldList?.let { list ->
                            val itemRemove = list.find {item -> itemFavorite.idBook == item.idBook }
                            itemRemove?.let {
                                list.remove(it)
                                _favoriteItem.value = list
                            }
                        }
                    }
                },
                onError = { error.value = it.message }
            )
        )
    }
}
