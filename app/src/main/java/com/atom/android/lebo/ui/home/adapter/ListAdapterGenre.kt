package com.atom.android.lebo.ui.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.databinding.ItemCategoryBinding
import com.atom.android.lebo.model.Genre
import com.atom.android.lebo.utils.extensions.loadImage

class ListAdapterGenre(private val onClick: (Genre) -> Unit) :
    BaseAdapter<Genre, BaseViewHolder<Genre>>(Genre.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Genre> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) : BaseViewHolder<Genre>(binding) {
        override fun binView(item: Genre) {
            super.binView(item)
            binding.apply {
                item.image?.let {
                    imageViewItem.loadImage(Uri.parse(item.image))
                }
                textViewItem.text = item.name
                imageViewItem.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
