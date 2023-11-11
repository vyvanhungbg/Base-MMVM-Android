package com.atom.android.lebo.ui.cart

import android.app.Dialog
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentCartBinding
import com.atom.android.lebo.utils.extensions.convertStrToMoney
import com.atom.android.lebo.utils.extensions.hasItem
import com.atom.android.lebo.utils.extensions.openDialogQuestion
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    override val viewModel by activityViewModel<CartViewModel>()

    private val cartAdapter by lazy {
        ListAdapterItemCart({ book ->
            val action =
                CartFragmentDirections.actionNavigationCartToNavigationDetail(book.id.toString())
            findNavController().navigate(action)
        }, { item, action ->
            viewModel.setItemInCart(context, item, action)
        })
    }

    override fun initData() {
        // late impl
    }

    override fun initView() {
        viewModel.getBooks()
        binding.recyclerViewCart.adapter = cartAdapter
        viewModel.apply {
            books.observe(viewLifecycleOwner) {
                cartAdapter.submitList(it.hasItem())
            }

            totalPrice.observe(viewLifecycleOwner) {
                binding.textViewTotalPrice.text = it.toString().convertStrToMoney()
            }

            isCheckedAll.observe(viewLifecycleOwner) {
                binding.checkboxAll.isChecked = it
            }
        }
    }

    override fun initEvent() {
        binding.apply {
            checkboxAll.setOnClickListener {
                viewModel.checkBoxAll(binding.checkboxAll.isChecked)
            }

            toolBarBack.setOnClickListener {
                findNavController().popBackStack()
            }

            textViewButtonOrder.setOnClickListener {
                val orderList = viewModel.getListOrdersDetail()
                if (orderList.isEmpty()) {
                    context?.let {
                        val dialogQuestion = Dialog(it)
                        dialogQuestion.openDialogQuestion(
                            it.getString(R.string.text_confirm),
                            it.getString(R.string.mess_bill_is_empty)
                        ) { dialogQuestion.dismiss() }
                    }
                } else {
                    findNavController().navigate(R.id.action_navigation_cart_to_navigation_checkout)
                }
            }
        }
    }
}
