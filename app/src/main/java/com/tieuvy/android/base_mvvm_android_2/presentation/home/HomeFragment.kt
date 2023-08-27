package com.tieuvy.android.base_mvvm_android_2.presentation.home

import com.tieuvy.android.base.BaseFragment
import com.tieuvy.android.base_mvvm_android_2.R
import com.tieuvy.android.base_mvvm_android_2.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "HomeFragment"

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeNavigation>() {

    override fun getLayoutId() = R.layout.fragment_home

    override val viewModel by viewModel<HomeViewModel>()

    override val navigation: HomeNavigation
        get() = HomeNavigation(this)


    override fun initData() {
    }

    override fun observeData() {
        binding.viewModel = viewModel

    }

    override fun setOnClick() {
        binding.setClickSearch {

            viewModel.textSearch.value?.let {
                context?.let { _context -> viewModel.searchUsersByName(_context, it) }
            }
        }
    }

}