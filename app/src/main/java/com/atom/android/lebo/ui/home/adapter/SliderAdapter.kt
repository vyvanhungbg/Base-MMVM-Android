package com.atom.android.lebo.ui.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.lebo.databinding.ItemSliderBinding
import com.atom.android.lebo.model.Slider
import com.atom.android.lebo.utils.extensions.loadImage
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderAdapterCustom>() {

    private val list = mutableListOf<Slider>()

    override fun getCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterCustom {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSliderBinding.inflate(inflater, parent, false)
        return SliderAdapterCustom(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterCustom, position: Int) {
        val item = list[position]
        viewHolder.bind(item)
    }

    fun submitList(slider: List<Slider>?) {
        slider?.let {
            list.clear()
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class SliderAdapterCustom(val binding: ItemSliderBinding) : ViewHolder(binding.root) {
        fun bind(item: Slider) {
            item.image?.let {
                binding.imageSlider.loadImage(Uri.parse(it))
            }
        }
    }
}
