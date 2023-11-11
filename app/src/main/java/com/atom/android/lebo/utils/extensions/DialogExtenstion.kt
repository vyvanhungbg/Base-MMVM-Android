package com.atom.android.lebo.utils.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import com.atom.android.lebo.R
import com.atom.android.lebo.databinding.DlQuestionYesNoBinding
import com.atom.android.lebo.utils.constants.Constant

fun Dialog.start(stopFlag: Boolean) {
    this.let {
        it.setContentView(R.layout.dl_progress_bar)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = Constant.DIALOGCONFIG.MARGIN_Y
                gravity = Gravity.CENTER
            }
        }
        it.setCancelable(stopFlag)
        it.show()
    }
}

fun Dialog.openDialogQuestion(title: String, content: String, confirmAction: () -> Unit) {
    this.let {
        val binding = DlQuestionYesNoBinding.inflate(layoutInflater)
        it.setContentView(binding.root)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = Constant.DIALOGCONFIG.MARGIN_Y
                gravity = Gravity.CENTER
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            confirmAction()
            dismiss()
        }
        binding.textViewTitle.text = title
        binding.textViewContent.text = content
        it.setCancelable(true)
        it.show()
    }
}
