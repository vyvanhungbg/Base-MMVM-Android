package com.atom.android.lebo.ui.detail.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.databinding.ItemBookVerticalBinding
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.utils.extensions.loadImage

class ListAdapterBookVertical(private val onClick: (Book) -> Unit) :
    BaseAdapter<Book, BaseViewHolder<Book>>(Book.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Book> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookVerticalBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemBookVerticalBinding) : BaseViewHolder<Book>(binding) {
        override fun binView(item: Book) {
            super.binView(item)
            binding.apply {
                item.image?.let {
                    imageViewItem.loadImage(Uri.parse(item.image))
                }
                textViewItem.text = item.title
                imageViewItem.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
