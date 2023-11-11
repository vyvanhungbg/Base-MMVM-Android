package com.atom.android.lebo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetail(
    val idBook: Int,
    val amount: Int,
    val price: Double,
) : Parcelable
