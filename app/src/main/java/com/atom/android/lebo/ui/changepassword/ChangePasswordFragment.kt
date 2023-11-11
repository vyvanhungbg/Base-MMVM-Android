package com.atom.android.lebo.ui.changepassword

import android.text.TextUtils
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentChangePasswordBinding
import com.atom.android.lebo.utils.extensions.showToast
import com.atom.android.lebo.utils.extensions.textChangesAfterTyping
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding>(FragmentChangePasswordBinding::inflate) {

    override val viewModel by viewModel<ChangedPasswordViewModel>()

    override fun initData() {
        // late impl
    }

    override fun initView() {
        viewModel.apply {
            validateForm.observe(viewLifecycleOwner) {
                binding.btnConfirm.isEnabled = it
            }
            oldPasswordValid.observe(viewLifecycleOwner) {
                if (it.first.not()) {
                    binding.textInputLayoutOldPassword.error = it.second
                } else {
                    binding.textInputLayoutOldPassword.isErrorEnabled = false
                }
            }
            newPasswordValid.observe(viewLifecycleOwner) {
                if (it.first.not()) {
                    binding.textInputLayoutNewPassword.error = it.second
                } else {
                    binding.textInputLayoutNewPassword.isErrorEnabled = false
                }
            }
            confirmPasswordValid.observe(viewLifecycleOwner) {
                if (it.first.not()) {
                    binding.textInputLayoutConfirmPassword.error = it.second
                } else {
                    binding.textInputLayoutConfirmPassword.isErrorEnabled = false
                }
            }
            changedPasswordState.observe(viewLifecycleOwner) {
                context?.showToast(it)
                findNavController().popBackStack()
            }
        }
    }

    override fun initEvent() {
        val oldPasswordObservable =
            binding.textInputLayoutOldPassword.editText?.textChangesAfterTyping()
        val newPasswordObservable =
            binding.textInputLayoutNewPassword.editText?.textChangesAfterTyping()
        val confirmPasswordObservable =
            binding.textInputLayoutConfirmPassword.editText?.textChangesAfterTyping()

        viewModel.validatePassword(
            context,
            oldPasswordObservable,
            newPasswordObservable,
            confirmPasswordObservable
        )
        binding.btnConfirm.setOnClickListener {
            val oldPassword = binding.textInputLayoutOldPassword.editText?.text.toString()
            val newPassword = binding.textInputLayoutNewPassword.editText?.text.toString()
            val confirmPassword = binding.textInputLayoutConfirmPassword.editText?.text.toString()
            if (
                TextUtils.isEmpty(oldPassword) ||
                TextUtils.isEmpty(newPassword) ||
                TextUtils.isEmpty(confirmPassword)
            ) {
                context?.showToast(getString(R.string.mess_field_blank))
            } else {
                viewModel.changedPassword(oldPassword, newPassword)
            }
        }
        binding.tbBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
