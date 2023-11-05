package com.tieuvy.android.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tieuvy.android.base.extension.showLoading
import com.tieuvy.android.base.extension.showToast


abstract class BaseFragment<VBinding : ViewDataBinding, N : BaseNavigation> : Fragment() {

    private var _binding: VBinding? = null
    protected val binding: VBinding
        get() = _binding as VBinding

    open var isUseBackPress = true

    private val dialogLoading by lazy { context?.let { Dialog(it) } }

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected abstract val viewModel: BaseViewModel


    protected abstract val navigation: N

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(isUseBackPress){
            activity?.onBackPressedDispatcher?.addCallback(this,object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    if(!onPreventBackPress()){
                        navigation.popBackStack()
                    }
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hasError.observe(viewLifecycleOwner){_message ->
            _message?.let {
                context?.showToast(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it){
                if(dialogLoading?.isShowing == false)
                    dialogLoading?.showLoading(viewLifecycleOwner)
            }else{
                dialogLoading?.dismiss()
            }
        }
        setView()
        observeData()
        setOnClick()
    }

    abstract fun initData()

    abstract fun observeData()
    abstract fun setView()

    abstract fun setOnClick()

    override fun onDestroyView() {
        dialogLoading?.dismiss()
        super.onDestroyView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    /**
     * Xử lý backPress cho fragment
     */
    open fun onPreventBackPress(): Boolean {
        return false
    }

    fun startActivityIfExists(intent: Intent, error: (() -> Unit)? = null) {
        intent.apply {
            flags = flags or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        kotlin.runCatching {
            startActivity(intent)
        }.onFailure {
            error?.invoke()
        }
    }

     fun startActivityIfExists(action: String, uri: String?, error: (() -> Unit)? = null) {
        kotlin.runCatching {
            val intent = Intent(action, Uri.parse(uri))
            startActivityIfExists(intent, error)
        }.onFailure {
            error?.invoke()
        }
    }
}
