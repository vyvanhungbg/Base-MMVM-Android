package com.hungvv.base_mvvm_android_3.presentation.home_epoxy

import android.view.inputmethod.EditorInfo
import com.hungvv.base_mvvm_android_3.ItemUserBindingModel_
import com.hungvv.base_mvvm_android_3.R
import com.hungvv.base_mvvm_android_3.databinding.FragmentHomeEpoxyBinding
import com.tieuvy.android.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

private const val TAG = "HomeEpoxyFragment"

class HomeEpoxyFragment :
    BaseFragment<FragmentHomeEpoxyBinding, HomeEpoxyNavigation>() {

    override fun getLayoutId() = R.layout.fragment_home_epoxy

    override val viewModel by viewModel<HomeEpoxyViewModel>()
    override val navigation: HomeEpoxyNavigation
        get() = HomeEpoxyNavigation(this)

    override fun initData() {
        context?.let {
            viewModel.initSearchDefault(it)
        }
    }

    override fun observeData() {
        viewModel.users.observe(viewLifecycleOwner) {
            binding.recyclerViewUser.requestModelBuild()
        }
    }

    override fun setView() {
        binding.viewModel = viewModel
        initEpoxy()
    }

    override fun setOnClick() {
        binding.setOnSearchClick {
            viewModel.searchUsersByName(requireContext())
        }
        binding.textInputEditTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchUsersByName(requireContext())
            }
            true
        }
    }

    private fun initEpoxy() {
        binding.recyclerViewUser.withModels {
            viewModel.users.value?.forEachIndexed { index, user ->
                add(ItemUserBindingModel_()
                    .id(index)
                    .user(user)
                    .itemClick { _ -> navigation.navigateToDetail(user) })
            }
        }
    }
}