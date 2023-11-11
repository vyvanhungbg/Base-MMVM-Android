package com.atom.android.lebo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    val id: Int,
    val statusValue: String
) : Parcelable
