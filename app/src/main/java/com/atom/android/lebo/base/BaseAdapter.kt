package com.atom.android.lebo.base

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(
        AsyncDifferConfig.Builder(diffUtil)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build()
    ) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binView(getItem(position))
    }

    fun loadMore(recyclerView: RecyclerView?, handle: () -> Unit) {
        recyclerView?.apply {
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                        val sizeData = recyclerView.adapter?.itemCount?.minus(1)
                        if (
                            linearLayoutManager != null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                            sizeData &&
                            sizeData != -1
                        ) {
                            handle()
                        }
                    }
                }
            )
        }
    }
}
