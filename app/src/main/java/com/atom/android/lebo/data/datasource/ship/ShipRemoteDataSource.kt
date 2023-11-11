package com.atom.android.lebo.data.datasource.ship

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.api.service.UnAuthApiService
import com.atom.android.lebo.model.ShippingMethod
import io.reactivex.rxjava3.core.Single

class ShipRemoteDataSource(private val service: UnAuthApiService.ShippingMethodService) :
    IShipDataSource.Remote {

    override fun getMoneyShip(id: Int): Single<BaseResponse<ShippingMethod?>> {
        return service.getMoneyShip(id)
    }
}
