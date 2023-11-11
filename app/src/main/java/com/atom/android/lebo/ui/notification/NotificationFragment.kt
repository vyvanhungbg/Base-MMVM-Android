package com.atom.android.lebo.ui.notification

import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentNotificationBinding
import com.atom.android.lebo.model.Notification
import com.atom.android.lebo.utils.extensions.hasItem
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    override val viewModel by activityViewModel<NotificationViewModel>()

    private val notificationAdapter by lazy {
        ListAdapterNotification(::onClick)
    }

    override fun initData() {
        // late impl
    }

    override fun initView() {
        binding.recyclerView.adapter = notificationAdapter
        viewModel.getAllNotification()
        viewModel.notification.observe(viewLifecycleOwner) {
            notificationAdapter.submitList(it.hasItem())
        }
        viewModel.swipeRefreshState.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.swipeRefreshLayout.isEnabled = true
        }
    }

    override fun initEvent() {
        binding.toolBarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isEnabled = false
            viewModel.getAllNotification()
        }
    }

    private fun onClick(notification: Notification) {
        if (notification.isRead.not()) {
            viewModel.updateNotification(notification.id)
        }
        val action =
            NotificationFragmentDirections.actionNavigationNotificationToNavigationBill(notification.idOrder)
        findNavController().navigate(action)
    }
}
