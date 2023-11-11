package com.atom.android.lebo.ui.order

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.databinding.ItemOrderLineBinding
import com.atom.android.lebo.model.OrderLine
import com.atom.android.lebo.model.OrderLine.Companion.diffUtil
import com.atom.android.lebo.utils.extensions.convertStrToMoney
import com.atom.android.lebo.utils.extensions.loadImage

class ListAdapterBillDetail(
    private val onClick: (OrderLine) -> Unit,
) : BaseAdapter<OrderLine, BaseViewHolder<OrderLine>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<OrderLine> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderLineBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemOrderLineBinding) : BaseViewHolder<OrderLine>(binding) {
        override fun binView(item: OrderLine) {
            super.binView(item)
            binding.apply {
                imageItem.loadImage(Uri.parse(item.book.image))
                textViewPriceItem.text = item.price.toString().convertStrToMoney()
                textViewAmountItem.text = binding.root.context.getString(
                    R.string.text_amount,
                    item.amount
                )
                textViewNameItem.text = item.book.title
                itemView.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
