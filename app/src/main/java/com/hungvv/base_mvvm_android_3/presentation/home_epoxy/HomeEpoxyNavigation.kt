package com.hungvv.base_mvvm_android_3.presentation.home_epoxy

import com.hungvv.base_mvvm_android_3.R
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.presentation.home.HomeFragmentDirections
import com.tieuvy.android.base.BaseFragment
import com.tieuvy.android.base.BaseNavigation

/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/
private const val TAG = "HomeEpoxyNavigation"

class HomeEpoxyNavigation(val fragment: HomeEpoxyFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }

    fun navigateToDetail(user: User) {
        val direction = HomeEpoxyFragmentDirections.actionHomeEpoxyFragmentToDetailFragment(user)
        navigateTo(R.id.homeEpoxyFragment, direction)
    }
}