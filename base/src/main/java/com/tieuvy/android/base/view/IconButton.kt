package com.tieuvy.android.base.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.tieuvy.android.base.R
import com.tieuvy.android.base.databinding.LayoutIconButtonBinding


class IconButton : FrameLayout {
    private val viewBinding by lazy { LayoutIconButtonBinding.inflate(LayoutInflater.from(context)) }

    var text: String
        get() = viewBinding.textView.text.toString()
        set(value) {
            viewBinding.textView.text = value
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
//        viewBinding = LayoutInflater
//            .from(context)
//            .inflate(R.layout.layout_icon_button, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.IconButton).apply {
            showIcon(this)
            showIconBackgroundColor(this)
            showText(this)
            recycle()
        }
    }

    private fun showIcon(attributes: TypedArray) {
        val iconResId = attributes.getResourceId(R.styleable.IconButton_icon, -1)
        val icon = AppCompatResources.getDrawable(context, iconResId)
        viewBinding.imageViewSchema.setImageDrawable(icon)
    }


//    fun setIcon(@DrawableRes icon: Int){
//        val icon = AppCompatResources.getDrawable(context, icon)
//        viewBinding.imageViewSchema.setImageDrawable(icon)
//    }
//
//    fun setIconBackgroundColor(@ColorRes color: Int){
//        val color = ContextCompat.getColor(context, color)
//        (viewBinding.layoutImage.background.mutate() as GradientDrawable).setColor(color)
//    }

    private fun showIconBackgroundColor(attributes: TypedArray) {
        val color = attributes.getColor(
            R.styleable.IconButton_iconBackground,
            ContextCompat.getColor(context, R.color.green)
        )
        (viewBinding.layoutImage.background.mutate() as GradientDrawable).setColor(color)
    }

    private fun showText(attributes: TypedArray) {
        viewBinding.textView.text = attributes.getString(R.styleable.IconButton_text).orEmpty()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        viewBinding.imageViewSchema.isEnabled = enabled
        viewBinding.textView.isEnabled = enabled
    }
}