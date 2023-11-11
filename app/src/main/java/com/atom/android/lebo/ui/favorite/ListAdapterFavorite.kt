package com.atom.android.lebo.ui.favorite

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.data.local.FavoriteEntityLocal
import com.atom.android.lebo.databinding.ItemBookVerticalBinding
import com.atom.android.lebo.utils.extensions.loadImage

class ListAdapterFavorite(
    private val onClick: (FavoriteEntityLocal) -> Unit,
    private val onLongClick: (FavoriteEntityLocal) -> Unit
) :
    BaseAdapter<FavoriteEntityLocal, BaseViewHolder<FavoriteEntityLocal>>(FavoriteEntityLocal.diffUtil) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<FavoriteEntityLocal> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookVerticalBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemBookVerticalBinding) :
        BaseViewHolder<FavoriteEntityLocal>(binding) {
        override fun binView(item: FavoriteEntityLocal) {
            super.binView(item)
            binding.apply {
                item.url?.let {
                    imageViewItem.loadImage(Uri.parse(item.url))
                }
                textViewItem.text = item.title
                imageViewItem.setOnClickListener {
                    onClick(item)
                }
                imageViewItem.setOnLongClickListener {
                    onLongClick(item)
                    true
                }
            }
        }
    }
}
