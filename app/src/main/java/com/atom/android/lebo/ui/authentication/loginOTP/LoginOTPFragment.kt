package com.atom.android.lebo.ui.authentication.loginOTP

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentLoginOTPBinding
import com.atom.android.lebo.ui.main.MainViewModel
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.closeKeyboard
import com.atom.android.lebo.utils.extensions.isValidOTP
import com.atom.android.lebo.utils.extensions.showKeyboard
import com.atom.android.lebo.utils.extensions.showToast
import com.atom.android.lebo.utils.extensions.textCountDown
import com.jakewharton.rxbinding4.widget.textChanges
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginOTPFragment : BaseFragment<FragmentLoginOTPBinding>(FragmentLoginOTPBinding::inflate) {

    override val viewModel by viewModel<LoginOTPViewModel>()

    private val activityViewModel by activityViewModel<MainViewModel>()

    private var email: String? = null
    override fun initData() {
        email = arguments?.getString(Constant.BUNDLED.EMAIL)
    }

    override fun initView() {
        email?.let {
            binding.textViewNotificationLoginOtp.text =
                context?.getString(R.string.text_notification_login_otp, email)
        }

        viewModel.apply {

            loginState.observe(viewLifecycleOwner) {
                context?.showToast(it.toString())
                activityViewModel.getUser()
                activityViewModel.registerTokenNotification()
                findNavController().navigate(R.id.action_navigation_login_otp_to_navigation_home)
            }

            countDown.observe(viewLifecycleOwner) {
                binding.textViewCountDown.text = context?.textCountDown(it)
            }

            finishCountDown.observe(viewLifecycleOwner) {
                binding.textViewResend.isVisible = it
                binding.textViewCountDown.isVisible = !it
            }

            autoSubmitOTP.observe(viewLifecycleOwner) {
                activity?.closeKeyboard()
                viewModel.login(context, email, it.toString())
            }
            message.observe(viewLifecycleOwner) {
                context?.showToast(it)
                activity?.showKeyboard(binding.textInputOtp)
            }
        }
    }

    override fun initEvent() {
        viewModel.requestOTP(email)
        binding.textViewResend.setOnClickListener {
            binding.textViewResend.isVisible = false
            binding.textViewCountDown.isVisible = true
            binding.textInputOtp.text = null
            viewModel.requestOTP(email)
        }
        binding.textViewBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnLogin.setOnClickListener {
            val otp = binding.textInputOtp.text.toString()
            val isValidOTPResult = otp.isValidOTP(context)
            if (isValidOTPResult.first.not()) {
                context?.showToast(isValidOTPResult.second)
            } else {
                viewModel.login(context, email, otp)
                activity?.closeKeyboard()
            }
        }
        val textOTPObservable = binding.textInputOtp.textChanges()
            .filter { it.length == binding.textInputOtp.itemCount }
        viewModel.autoSubmitOTP(textOTPObservable)
    }
}
