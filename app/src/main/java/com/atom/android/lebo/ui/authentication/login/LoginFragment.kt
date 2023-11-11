package com.atom.android.lebo.ui.authentication.login

import android.text.TextUtils
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentLoginBinding
import com.atom.android.lebo.model.LoginEntity
import com.atom.android.lebo.ui.main.MainViewModel
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.isValidEmail
import com.atom.android.lebo.utils.extensions.showToast
import com.atom.android.lebo.utils.extensions.textChangesAfterTyping
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel by viewModel<LoginViewModel>()

    private val activityViewModel by activityViewModel<MainViewModel>()

    override fun initData() {
        // late impl
    }

    override fun initView() {

        viewModel.apply {
            validateEmail.observe(viewLifecycleOwner) {
                if (it.first.not()) {
                    binding.textInputLayoutEmail.error = it.second
                } else {
                    binding.textInputLayoutEmail.isErrorEnabled = false
                }
            }

            validatePassword.observe(viewLifecycleOwner) {
                if (it.first.not()) {
                    binding.textInputLayoutPassword.error = it.second
                } else {
                    binding.textInputLayoutPassword.isErrorEnabled = false
                }
            }

            validateForm.observe(viewLifecycleOwner) {
                binding.btnLogin.isEnabled = it
            }

            loginState.observe(viewLifecycleOwner) {
                context?.showToast(it.toString())
                activityViewModel.getUser()
                activityViewModel.registerTokenNotification()
                if (findNavController().popBackStack().not()) {
                    findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
                }
            }

            account.observe(viewLifecycleOwner) {
                binding.textInputLayoutEmail.editText?.setText(it.userEmail)
                binding.textInputLayoutPassword.editText?.setText(it.userPassword)
            }
        }
    }

    override fun initEvent() {
        val emailObservable =
            binding.textInputLayoutEmail.editText?.textChangesAfterTyping()
        val passwordObservable =
            binding.textInputLayoutPassword.editText?.textChangesAfterTyping()
        viewModel.validateForm(context, emailObservable, passwordObservable)

        binding.btnLogin.setOnClickListener {
            val email = binding.textInputLayoutEmail.editText?.text.toString()
            val password = binding.textInputLayoutPassword.editText?.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                context?.showToast(getString(R.string.mess_field_blank))
            } else {
                viewModel.login(email, password)
            }
        }
        binding.textViewForgotPassword.setOnClickListener {
            val email = binding.textInputLayoutEmail.editText?.text.toString()
            val validateEmailResult = email.isValidEmail(context)
            if (validateEmailResult.first) {
                findNavController().navigate(
                    R.id.action_navigation_login_to_navigation_forgot_password,
                    bundleOf(Pair(Constant.BUNDLED.EMAIL, email))
                )
            } else {
                findNavController().navigate(R.id.action_navigation_login_to_navigation_forgot_password)
            }
        }

        binding.textViewLoginOtp.setOnClickListener {
            val email = binding.textInputLayoutEmail.editText?.text.toString()
            val validateEmailResult = email.isValidEmail(context)
            if (validateEmailResult.first.not()) {
                context?.showToast(validateEmailResult.second)
            } else {
                findNavController().navigate(
                    R.id.action_navigation_login_to_navigation_login_otp,
                    bundleOf(Pair(Constant.BUNDLED.EMAIL, email))
                )
            }
        }
    }

    override fun onStop() {
        val email = binding.textInputLayoutEmail.editText?.text.toString()
        val password = binding.textInputLayoutPassword.editText?.text.toString()
        viewModel.setAccount(LoginEntity(email, password))
        super.onStop()
    }
}
