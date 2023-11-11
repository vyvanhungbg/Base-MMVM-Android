package com.atom.android.lebo.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.repository.bill.BillRepository
import com.atom.android.lebo.model.Bill

class OrderDetailViewModel(private val repository: BillRepository) : BaseViewModel() {

    private val _bill = MutableLiveData<Bill?>()
    val bill: LiveData<Bill?>
        get() = _bill

    fun getBillByID(id: Int) {
        registerDisposable(
            executeTask(
                task = { repository.getBillByID(id) },
                onSuccess = {
                    _bill.value = it.data
                },
                onError = {
                    error.value = it.message
                }
            )
        )
    }
}
