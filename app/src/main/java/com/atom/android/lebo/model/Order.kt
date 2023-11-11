package com.atom.android.lebo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val address: String,
    val idShippingMethod: String,
    val note: String,
    val orderDetail: List<OrderDetail>,
    val phone: String,
    val receiver: String
) : Parcelable
