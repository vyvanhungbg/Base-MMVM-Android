package com.atom.android.lebo.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName(value = "data") val data: T?,
    @SerializedName(value = "status") val status: Boolean,
    @SerializedName(value = "message") val message: String
)
