package com.atom.android.lebo.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderLine(
    val amount: Int,
    val book: Book,
    val price: Double
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OrderLine>() {
            override fun areItemsTheSame(oldItem: OrderLine, newItem: OrderLine): Boolean {
                return oldItem.book.id == newItem.book.id
            }

            override fun areContentsTheSame(oldItem: OrderLine, newItem: OrderLine): Boolean {
                return oldItem == newItem
            }
        }
    }
}
