package com.atom.android.lebo.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.atom.android.lebo.utils.constants.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bill(
    val id: Int,
    val shippingMethod: ShippingMethod,
    val address: String,
    val note: String,
    val phone: String,
    val receiver: String,
    val orderHistories: List<OrderHistory>,
    val orderLines: List<OrderLine>,
    val createdAt: String,
) : Parcelable {
    fun totalBill() = totalPriceOfItems() + shippingMethod.cost

    fun totalPriceOfItems() = orderLines.sumOf { it.amount * it.price }

    fun totalItem() = orderLines.sumOf { it.amount }

    fun getTime(type: Int) =
        orderHistories.find { orderHistory -> orderHistory.status.id == type }?.statusDate
            ?: Constant.DEFAULT.STRING

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Bill>() {
            override fun areItemsTheSame(oldItemSearch: Bill, newItemSearch: Bill): Boolean {
                return oldItemSearch.id == newItemSearch.id
            }

            override fun areContentsTheSame(oldItemSearch: Bill, newItemSearch: Bill): Boolean {
                return oldItemSearch == newItemSearch
            }
        }
    }
}
