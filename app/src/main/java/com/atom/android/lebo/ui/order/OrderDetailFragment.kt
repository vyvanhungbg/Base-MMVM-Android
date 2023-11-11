package com.atom.android.lebo.ui.order

import android.content.ClipData
import android.content.ClipboardManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentOrderDetailBinding
import com.atom.android.lebo.utils.constants.ApiConstant
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.convertInstantToDate
import com.atom.android.lebo.utils.extensions.convertStrToMoney
import com.atom.android.lebo.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderDetailFragment :
    BaseFragment<FragmentOrderDetailBinding>(FragmentOrderDetailBinding::inflate) {

    override val viewModel by viewModel<OrderDetailViewModel>()

    private val listAdapter = ListAdapterBillDetail { {} }

    private val safeArgs: OrderDetailFragmentArgs by navArgs()

    override fun initData() {
        viewModel.getBillByID(safeArgs.bundleId)
    }

    override fun initView() {
        viewModel.bill.observe(viewLifecycleOwner) {
            it?.let {
                binding.apply {
                    textViewInfoIdBill.text = it.id.toString()
                    recyclerviewItem.adapter = listAdapter
                    textViewInfoDelivery.text = it.shippingMethod.name
                    textViewInfoLocation.text = it.address
                    textTotalPrice.text = it.totalPriceOfItems().toString().convertStrToMoney()
                    textViewTotalBill.text = it.totalBill().toString().convertStrToMoney()
                    textViewPriceShip.text = it.shippingMethod.cost.toString().convertStrToMoney()
                    textViewInfoPhone.text = it.phone
                    val timeOrder = it.getTime(ApiConstant.TYPEOFBILL.PENDING)
                    if (timeOrder != Constant.DEFAULT.STRING) {
                        layoutTimeOrder.isVisible = true
                        textViewTimeOrder.text = timeOrder.convertInstantToDate()
                    }
                    val timeAccept = it.getTime(ApiConstant.TYPEOFBILL.ACCEPT)
                    if (timeAccept != Constant.DEFAULT.STRING) {
                        layoutTimeConfirm.isVisible = true
                        textViewTimeConfirm.text = timeAccept.convertInstantToDate()
                    }
                    val timeDelivery = it.getTime(ApiConstant.TYPEOFBILL.DELIVERY)
                    if (timeDelivery != Constant.DEFAULT.STRING) {
                        layoutTimeDelivery.isVisible = true
                        textViewTimeDelivery.text = timeDelivery.convertInstantToDate()
                    }
                    val timeSuccess = it.getTime(ApiConstant.TYPEOFBILL.SUCCESS)
                    if (timeSuccess != Constant.DEFAULT.STRING) {
                        layoutTimeSuccess.isVisible = true
                        textViewTimeSuccess.text = timeSuccess.convertInstantToDate()
                    }
                    listAdapter.submitList(it.orderLines)
                }
            }
        }
    }

    override fun initEvent() {
        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textViewInfoIdBill.setOnClickListener {
            context?.let {
                val clipboard: ClipboardManager? =
                    getSystemService(it, ClipboardManager::class.java)
                val clip = ClipData.newPlainText(null, binding.textViewInfoIdBill.text.toString())
                clipboard?.setPrimaryClip(clip)
                context?.showToast(R.string.text_copy_success)
            }
        }
    }
}
