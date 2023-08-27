package com.tieuvy.android.base_mvvm_android_2.presentation.home

import com.tieuvy.android.base.BaseFragment
import com.tieuvy.android.base.BaseNavigation

class HomeNavigation(private val fragment: HomeFragment) : BaseNavigation() {
    override fun fragment(): BaseFragment<*, *> = fragment
}