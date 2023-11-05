package com.hungvv.base_mvvm_android_3.presentation.home

import com.hungvv.base_mvvm_android_3.R
import com.hungvv.base_mvvm_android_3.data.model.User
import com.tieuvy.android.base.BaseFragment
import com.tieuvy.android.base.BaseNavigation
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/
private const val TAG = "HomeNavigation"

class HomeNavigation(val fragment: HomeFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }

    fun navigateToDetail(user: User) {
        val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(user)
        navigateTo(R.id.homeFragment, direction)
    }
}