package com.hungvv.base_mvvm_android_3.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponseGitHub<T>(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_results")
    val inCompleteResults: Boolean = false,
    val items: List<T>? = null
)