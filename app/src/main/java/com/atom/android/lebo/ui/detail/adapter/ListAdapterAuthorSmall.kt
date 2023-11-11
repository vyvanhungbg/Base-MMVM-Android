package com.atom.android.lebo.ui.detail.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.databinding.ItemAuthorSmallBinding
import com.atom.android.lebo.model.Author
import com.atom.android.lebo.utils.extensions.loadImage

class ListAdapterAuthorSmall(private val onClick: (Author) -> Unit) :
    BaseAdapter<Author, BaseViewHolder<Author>>(Author.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Author> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAuthorSmallBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemAuthorSmallBinding) : BaseViewHolder<Author>(binding) {
        override fun binView(item: Author) {
            super.binView(item)
            binding.apply {
                item.image?.let {
                    imageViewAuthor.loadImage(Uri.parse(item.image))
                }
                textViewNameAuthor.text = item.authorName
                imageViewAuthor.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
