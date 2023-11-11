package com.atom.android.lebo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingMethod(
    val id: Int,
    val name: String,
    val cost: Double,
    val distanceAbove: Double,
) : Parcelable
