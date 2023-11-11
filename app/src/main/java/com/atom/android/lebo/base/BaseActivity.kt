package com.atom.android.lebo.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VBinding : ViewBinding>(private val bindingInflater: (LayoutInflater) -> VBinding) :
    AppCompatActivity() {

    private var _binding: VBinding? = null
    protected val binding: VBinding
        get() = _binding as VBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        initData()
        bindData()
        handleEvent()
    }

    abstract fun initData()

    abstract fun handleEvent()

    abstract fun bindData()

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
