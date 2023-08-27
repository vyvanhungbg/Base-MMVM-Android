package com.tieuvy.android.base_mvvm_android_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BaseResponseGitHub<T>(
    @SerialName("total_count")
    val totalCount: Int = 0,
    @SerialName("incomplete_results")
    val inCompleteResults: Boolean = false,
    val items: List<T>? = null
)