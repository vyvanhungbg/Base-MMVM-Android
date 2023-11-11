package com.atom.android.lebo.ui.account

import android.app.Dialog
import android.net.Uri
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentAccountBinding
import com.atom.android.lebo.ui.main.MainViewModel
import com.atom.android.lebo.utils.extensions.loadImage
import com.atom.android.lebo.utils.extensions.openDialogQuestion
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    override val viewModel by viewModel<AccountViewModel>()

    private val activityViewModel by activityViewModel<MainViewModel>()

    override fun initData() {
        // late impl
    }

    override fun initView() {
        viewModel.logoutState.observe(viewLifecycleOwner) {
            activityViewModel.clearUser()
            findNavController().navigate(R.id.action_navigation_account_to_navigation_login)
        }
        activityViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    btnLogout.isVisible = it.status
                    btnLogin.isVisible = !it.status
                    if (it.status) {
                        val user = it.data
                        user?.let {
                            imageUser.loadImage(Uri.parse(it.image))
                            textViewNameUser.text = it.name
                        }
                    } else {
                        textViewNameUser.text = getString(R.string.text_notification_not_login)
                    }
                }
            } else {
                binding.textViewNameUser.text = getString(R.string.text_notification_not_login)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_main_green))
    }

    override fun initEvent() {

        binding.apply {
            btnLogout.setOnClickListener {
                context?.let {
                    Dialog(it).openDialogQuestion(
                        getString(R.string.text_confirm),
                        getString(R.string.text_question_logout)
                    ) {
                        viewModel.logout()
                    }
                }
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_account_to_navigation_login)
            }

            swipeRefreshLayout.setOnRefreshListener {
                activityViewModel.getUser()
            }

            layoutChangedPassword.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_account_to_navigation_change_password)
            }
        }
    }
}
