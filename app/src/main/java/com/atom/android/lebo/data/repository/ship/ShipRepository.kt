package com.atom.android.lebo.data.repository.ship

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.data.datasource.ship.IShipDataSource
import com.atom.android.lebo.model.ShippingMethod
import io.reactivex.rxjava3.core.Single

class ShipRepository(private val remote: IShipDataSource.Remote) : IShipRepository {

    override fun getMoneyShip(id: Int): Single<BaseResponse<ShippingMethod?>> {
        return remote.getMoneyShip(id)
    }
}
