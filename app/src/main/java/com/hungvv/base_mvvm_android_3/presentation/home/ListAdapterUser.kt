package com.hungvv.base_mvvm_android_3.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hungvv.base_mvvm_android_3.R
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.databinding.ItemUserBinding
import com.tieuvy.android.base.BaseAdapter
import com.tieuvy.android.base.BaseViewHolder


/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

class ListAdapterUser(
    private val onClick: (User) -> Unit,
) :
    BaseAdapter<User, BaseViewHolder<User>>(User.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<User> {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemUserBinding>(inflater, R.layout.item_user, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemUserBinding) :
        BaseViewHolder<User>(binding) {
        override fun binView(item: User) {
            super.binView(item)
            binding.apply {
                user = item
                itemClick = View.OnClickListener {
                    onClick.invoke(item)
                }
                textViewUrl.isSelected = true
            }
        }
    }
}