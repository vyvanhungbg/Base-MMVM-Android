package com.tieuvy.android.base.extension

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.tieuvy.android.base.databinding.DlCommonLoadingBinding


fun Dialog.showLoading(
    lifecycle: LifecycleOwner,
    binding: ViewBinding = DlCommonLoadingBinding.inflate(layoutInflater),
    isTouchOutSideCancelable: Boolean = false,
    marginY: Int = -170,
) {

    if (lifecycle.lifecycle.currentState == androidx.lifecycle.Lifecycle.State.DESTROYED)
        return
    setContentView(binding.root)
    window?.apply {
        setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        attributes.apply {
            y = marginY
            gravity = Gravity.CENTER
        }
    }
    setCancelable(isTouchOutSideCancelable)
    setCanceledOnTouchOutside(false)
    show()
}
