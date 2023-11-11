package com.atom.android.lebo.ui.checkout

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.databinding.FragmentCheckoutBinding
import com.atom.android.lebo.model.Order
import com.atom.android.lebo.ui.cart.CartViewModel
import com.atom.android.lebo.ui.main.MainViewModel
import com.atom.android.lebo.utils.constants.ApiConstant
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.convertStrToMoney
import com.atom.android.lebo.utils.extensions.openDialogQuestion
import com.atom.android.lebo.utils.extensions.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(FragmentCheckoutBinding::inflate) {

    override val viewModel by viewModel<CheckoutViewModel>()

    private val cartViewModel by activityViewModel<CartViewModel>()

    private val activityViewModel by activityViewModel<MainViewModel>()

    private val listOrder by lazy { cartViewModel.getListOrdersDetail() }

    private var idShippingMethod = Constant.DEFAULT.ID_SHIPPING_METHOD

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        if (isGranted.any { it.value == false }.not()) {
            getLastLocation()
        } else {
            context?.showToast(R.string.error_permission_location)
        }
    }

    override fun initData() {
        viewModel.getMoneyOfShip(idShippingMethod)
        viewModel.calcTotalPrice(listOrder)
    }

    override fun initView() {
        getLastLocation()

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.textViewTotalPrice.text = it.toString().convertStrToMoney()
        }

        viewModel.moneyOfShip.observe(viewLifecycleOwner) {
            binding.textViewMoneyOfShip.text = it?.cost.toString().convertStrToMoney()
            it?.let {
                idShippingMethod = it.id
            }
        }

        viewModel.totalMoneyToBePaid.observe(viewLifecycleOwner) {
            binding.textViewTotalMoneyToBePaid.text = it.toString().convertStrToMoney()
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.textViewTotalPrice.text = it.toString().convertStrToMoney()
        }

        viewModel.locationName.observe(viewLifecycleOwner) {
            binding.editTextGetLocationInCheckout.setText(it.toString())
        }
        viewModel.orderState.observe(viewLifecycleOwner) {
            context?.showToast(it.toString())
            findNavController().popBackStack()
        }

        activityViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                val user = it.data
                if (it.status && user != null) {
                    binding.editTextPhone.setText(user.phone)
                    binding.editTextReceiver.setText(user.name)
                }
            }
        }
    }

    override fun initEvent() {
        binding.radioGroup.setOnCheckedChangeListener { _, position ->
            when (position) {
                R.id.radio_button_cash_on_delivery -> {
                    idShippingMethod = ApiConstant.SHIPPINGMETHOD.COD
                }
                R.id.radio_button_payment_by_wallet -> {
                    idShippingMethod = ApiConstant.SHIPPINGMETHOD.PAYMENT
                }
            }
            viewModel.getMoneyOfShip(idShippingMethod)
        }
        binding.apply {
            toolBarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            textViewAddLocationByMap.setOnClickListener {
                getLastLocation()
                context?.showToast(R.string.mess_get_location)
            }
        }
        binding.btnOrderInCheckout.setOnClickListener {
            val userResponse = activityViewModel.user.value
            if (userResponse == null || !userResponse.status) {
                context?.let { context ->
                    val dialog = Dialog(context)
                    val title = getString(R.string.title_required_login)
                    val message = getString(R.string.messsage_required_login)
                    val action =
                        CheckoutFragmentDirections.actionNavigationCheckoutToNavigationLogin()
                    dialog.openDialogQuestion(
                        title,
                        message
                    ) { findNavController().navigate(action) }
                }
            } else {
                val address = binding.editTextGetLocationInCheckout.text.toString().trim()
                val note = binding.editTextNote.text.toString().trim()
                val phone = binding.editTextPhone.text.toString().trim()
                val receiver = binding.editTextReceiver.text.toString().trim()
                val order =
                    Order(address, idShippingMethod.toString(), note, listOrder, phone, receiver)
                viewModel.createNewBill(context, order)
            }
        }
    }

    private fun getLastLocation() {
        activity?.let {
            val fusedLocationProviderClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(it)
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            } else {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        viewModel.getLocationName(it, task.result)
                    }
                }
            }
        }
    }
}
