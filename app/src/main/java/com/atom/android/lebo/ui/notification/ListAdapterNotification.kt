package com.atom.android.lebo.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseAdapter
import com.atom.android.lebo.base.BaseViewHolder
import com.atom.android.lebo.databinding.ItemNotificationBinding
import com.atom.android.lebo.model.Notification
import com.atom.android.lebo.utils.extensions.convertInstantToDate

class ListAdapterNotification(private val onClick: (Notification) -> Unit) :
    BaseAdapter<Notification, BaseViewHolder<Notification>>(Notification.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Notification> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemNotificationBinding) :
        BaseViewHolder<Notification>(binding) {
        override fun binView(item: Notification) {
            super.binView(item)
            binding.apply {
                textViewTitle.text = item.title
                textViewBody.text = item.body
                if (item.isRead) {
                    layoutItem.background = null
                } else {
                    layoutItem.background =
                        ContextCompat.getDrawable(binding.root.context, R.color.color_gray_500)
                }
                textViewTime.text = item.createdAt.convertInstantToDate()
                root.setOnClickListener {
                    onClick(item)
                    layoutItem.background = null
                }
            }
        }
    }
}
