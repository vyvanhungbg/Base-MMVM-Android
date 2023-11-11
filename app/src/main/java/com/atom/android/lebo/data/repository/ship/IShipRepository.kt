package com.atom.android.lebo.data.repository.ship

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.ShippingMethod
import io.reactivex.rxjava3.core.Single

interface IShipRepository {

    fun getMoneyShip(id: Int): Single<BaseResponse<ShippingMethod?>>
}
