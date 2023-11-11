package com.atom.android.lebo.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val id: Int,
    val authorName: String?,
    val description: String?,
    val image: String?
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Author>() {
            override fun areItemsTheSame(oldItem: Author, newItem: Author) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Author, newItem: Author) = oldItem == newItem
        }
    }
}
