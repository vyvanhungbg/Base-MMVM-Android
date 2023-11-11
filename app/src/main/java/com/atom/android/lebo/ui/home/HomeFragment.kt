package com.atom.android.lebo.ui.home

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentHomeBinding
import com.atom.android.lebo.ui.home.adapter.ListAdapterAuthor
import com.atom.android.lebo.ui.home.adapter.ListAdapterBook
import com.atom.android.lebo.ui.home.adapter.ListAdapterGenre
import com.atom.android.lebo.ui.home.adapter.SliderAdapter
import com.atom.android.lebo.ui.notification.NotificationViewModel
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.hasItem
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by viewModel<HomeViewModel>()

    private val notificationViewModel by activityViewModel<NotificationViewModel>()

    private val genreAdapter by lazy { ListAdapterGenre({}) }
    private val bookAdapter by lazy {
        ListAdapterBook { book ->
            val action = HomeFragmentDirections
                .actionNavigationHomeToNavigationDetail(book.id.toString())
            findNavController().navigate(action)
        }
    }
    private val sliderAdapter by lazy { SliderAdapter() }
    private val authorAdapter by lazy { ListAdapterAuthor({}) }

    override fun initData() {
        viewModel.getGenre()
        viewModel.getBook()
        viewModel.getSlider()
        viewModel.getAuthor()
    }

    override fun initView() {
        viewModel.getAmountCart()
        binding.apply {
            recyclerViewCategory.adapter = genreAdapter
            recyclerViewBook.adapter = bookAdapter
            recyclerViewAuthor.adapter = authorAdapter
            sliderView.apply {
                setIndicatorAnimation(IndicatorAnimationType.WORM)
                setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
                startAutoCycle()
            }
            sliderView.setSliderAdapter(sliderAdapter)
        }

        viewModel.apply {
            genre.observe(viewLifecycleOwner) {
                genreAdapter.submitList(it.hasItem())
            }

            books.observe(viewLifecycleOwner) {
                bookAdapter.submitList(it.hasItem())
            }

            slides.observe(viewLifecycleOwner) {
                sliderAdapter.submitList(it)
            }

            author.observe(viewLifecycleOwner) {
                authorAdapter.submitList(it.hasItem())
            }

            swipeRefreshState.observe(viewLifecycleOwner) {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.swipeRefreshLayout.isEnabled = true
                binding.sliderView.setSliderAdapter(sliderAdapter)
                binding.recyclerViewBook.smoothScrollToPosition(Constant.DEFAULT.POSITION)
                binding.recyclerViewAuthor.smoothScrollToPosition(Constant.DEFAULT.POSITION)
            }
        }

        notificationViewModel.notification.observe(viewLifecycleOwner) {
            val amountNotification = it?.count { item -> item.isRead.not() }
            val visibleBadge =
                amountNotification != null && amountNotification > Constant.DEFAULT.AMOUNT_NOTIFICATION
            if (visibleBadge) {
                binding.textViewAmountNotification.text = amountNotification.toString()
            }
            binding.textViewAmountNotification.isVisible = visibleBadge
        }

        viewModel.amountCart.observe(viewLifecycleOwner) {
            val visibleBadge = it != Constant.DEFAULT.ITEM_CART
            if (visibleBadge) {
                binding.textViewAmountCart.text = it.toString()
            }
            binding.textViewAmountCart.isVisible = visibleBadge
        }
    }

    override fun initEvent() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isEnabled = false
                viewModel.refreshData(context)
            }
            layoutCart.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_cart)
            }
            layoutNotification.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_notification)
            }
        }
        bookAdapter.loadMore(binding.recyclerViewBook) { viewModel.getBook() }
    }
}
