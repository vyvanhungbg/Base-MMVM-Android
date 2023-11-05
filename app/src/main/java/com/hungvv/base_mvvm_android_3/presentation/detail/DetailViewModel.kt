package com.hungvv.base_mvvm_android_3.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungvv.base_mvvm_android_3.data.model.User
import com.tieuvy.android.base.BaseViewModel
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/
private const val TAG = "DetailViewModel"

class DetailViewModel(

) : BaseViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun setData(safeArgs: DetailFragmentArgs){
        _user.value = safeArgs.user
    }
}

