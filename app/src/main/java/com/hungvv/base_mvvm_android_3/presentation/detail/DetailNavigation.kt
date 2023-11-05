package com.hungvv.base_mvvm_android_3.presentation.detail

import com.tieuvy.android.base.BaseFragment
import com.tieuvy.android.base.BaseNavigation
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/
private const val TAG = "DetailNavigation"

class DetailNavigation(val fragment: DetailFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}