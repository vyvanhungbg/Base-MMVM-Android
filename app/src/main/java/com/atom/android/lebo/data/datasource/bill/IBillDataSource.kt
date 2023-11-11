package com.atom.android.lebo.data.datasource.bill

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Bill
import com.atom.android.lebo.model.Order
import io.reactivex.rxjava3.core.Single

interface IBillDataSource {

    interface Remote {
        fun createOrder(order: Order): Single<BaseResponse<Bill>>
        fun getBill(type: Int): Single<BaseResponse<List<Bill>?>>
        fun getBillByID(id: Int): Single<BaseResponse<Bill>>
    }
}
