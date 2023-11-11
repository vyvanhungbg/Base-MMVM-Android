package com.atom.android.lebo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderHistory(
    val reason: String,
    val status: Status,
    val statusDate: String
) : Parcelable
