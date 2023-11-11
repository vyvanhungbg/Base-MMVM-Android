package com.atom.android.lebo.data.datasource.bill

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.AuthApiService
import com.atom.android.lebo.model.Bill
import com.atom.android.lebo.model.Order
import io.reactivex.rxjava3.core.Single

class BillRemoteDataSource(private val billService: AuthApiService.BillService) :
    IBillDataSource.Remote {

    override fun createOrder(order: Order): Single<BaseResponse<Bill>> {
        return billService.createOrder(order)
    }

    override fun getBill(type: Int): Single<BaseResponse<List<Bill>?>> {
        return billService.getBill(type)
    }

    override fun getBillByID(id: Int): Single<BaseResponse<Bill>> {
        return billService.getBillByID(id)
    }
}
