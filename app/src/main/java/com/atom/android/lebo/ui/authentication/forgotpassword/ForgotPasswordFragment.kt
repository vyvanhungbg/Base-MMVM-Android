package com.atom.android.lebo.ui.authentication.forgotpassword

import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentForgotPasswordBinding
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.ignoreFastAction
import com.atom.android.lebo.utils.extensions.showToast
import com.atom.android.lebo.utils.extensions.withIOToMainThread
import com.jakewharton.rxbinding4.widget.textChanges
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    override val viewModel by viewModel<ForgotPasswordViewModel>()

    override fun initData() {
        // late impl
    }

    override fun initView() {
        val email = arguments?.getString(Constant.BUNDLED.EMAIL)
        email?.let {
            binding.textInputLayoutEmail.editText?.setText(it)
        }
        viewModel.apply {

            validateEmail.observe(viewLifecycleOwner) {
                binding.btnForgotPassword.isEnabled = it.first
                binding.textInputLayoutEmail.isErrorEnabled = !it.first
                if (it.first.not()) {
                    binding.textInputLayoutEmail.error = it.second
                }
            }

            requestSuccess.observe(viewLifecycleOwner) {
                context?.showToast(it)
            }
        }
    }

    override fun initEvent() {
        val observableEmail =
            binding.textInputLayoutEmail.editText
                ?.textChanges()
                ?.ignoreFastAction()
                ?.withIOToMainThread()
        viewModel.validateEmail(context, observableEmail)
        binding.btnForgotPassword.setOnClickListener {
            viewModel.requestForgotPassword(binding.textInputLayoutEmail.editText?.text.toString())
        }
        binding.textViewLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
