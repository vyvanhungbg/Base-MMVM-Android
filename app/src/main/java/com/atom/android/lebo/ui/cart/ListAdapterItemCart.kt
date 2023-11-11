package com.atom.android.lebo.ui.cart

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.data.local.CartEntityLocal
import com.atom.android.lebo.databinding.ItemCartBinding
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.utils.extensions.actionCart
import com.atom.android.lebo.utils.extensions.convertStrToMoney
import com.atom.android.lebo.utils.extensions.loadImage

class ListAdapterItemCart(
    private val onClick: (Book) -> Unit,
    private val onUpdate: (CartEntityLocal, action: ACTION) -> Unit
) :
    BaseAdapter<Book, BaseViewHolder<Book>>(Book.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Book> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCartBinding) :
        BaseViewHolder<Book>(binding) {
        override fun binView(item: Book) {
            super.binView(item)
            binding.apply {
                item.image?.let {
                    imageViewItem.loadImage(Uri.parse(item.image))
                }
                textViewNameItem.text = item.title
                root.setOnClickListener {
                    onClick(item)
                }
                textViewAuthor.text = item.getAllNameGenres()
                btnAmount.number = item.amount.toString()
                checkboxItem.isChecked = item.isChecked
                textViewPrice.text = item.price.toString().convertStrToMoney()
                btnAmount.setOnValueChangeListener { _, oldValue, newValue ->
                    val action = actionCart(oldValue, newValue)
                    val newItem = CartEntityLocal(item.id, newValue, binding.checkboxItem.isChecked)
                    onUpdate(newItem, action)
                }
                binding.checkboxItem.setOnClickListener {
                    val newItem = CartEntityLocal(
                        item.id,
                        binding.btnAmount.number.toInt(),
                        binding.checkboxItem.isChecked
                    )
                    onUpdate(newItem, ACTION.ASC)
                }
            }
        }
    }
}
