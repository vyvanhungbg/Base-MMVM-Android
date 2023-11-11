package com.atom.android.lebo.ui.checkout

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseViewModel
import com.atom.android.lebo.data.local.CartEntityLocalDAO
import com.atom.android.lebo.data.repository.bill.BillRepository
import com.atom.android.lebo.data.repository.ship.ShipRepository
import com.atom.android.lebo.model.Order
import com.atom.android.lebo.model.OrderDetail
import com.atom.android.lebo.model.ShippingMethod
import com.atom.android.lebo.utils.constants.Constant
import java.io.IOException
import java.util.Locale


class CheckoutViewModel(
    private val repository: BillRepository,
    private val shipRepository: ShipRepository,
    private val cartEntityLocalDAO: CartEntityLocalDAO
) : BaseViewModel() {

    private val _orderState = MutableLiveData<String>()
    val orderState: LiveData<String>
        get() = _orderState

    private val _moneyOfShip = MutableLiveData<ShippingMethod?>()
    val moneyOfShip: LiveData<ShippingMethod?>
        get() = _moneyOfShip

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double>
        get() = _totalPrice

    private val _totalMoneyToBePaid = MediatorLiveData<Double>()
    val totalMoneyToBePaid: LiveData<Double>
        get() = _totalMoneyToBePaid

    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String>
        get() = _locationName

    init {
        _totalMoneyToBePaid.addSource(_totalPrice) {
            calcMoneyToBePaid()
        }
        _totalMoneyToBePaid.addSource(_moneyOfShip) {
            calcMoneyToBePaid()
        }
    }

    private fun calcMoneyToBePaid() {
        val totalPrice = _totalPrice.value
        val moneyOfShip = _moneyOfShip.value
        if (totalPrice != null && moneyOfShip != null) {
            val money = totalPrice + moneyOfShip.cost
            _totalMoneyToBePaid.value =
                if (money >= Constant.DEFAULT.PRICE) money else Constant.DEFAULT.PRICE
        }
    }

    fun createNewBill(context: Context?, order: Order) {
        val validateOrder = validateOrder(context, order)
        if (validateOrder.first) {
            registerDisposable(
                executeTask(
                    task = { repository.createOrder(order) },
                    onSuccess = {
                        if (it.status) {
                            deleteItemOrdered(it.message, order)
                        }
                    },
                    onError = {
                        error.value = it.toString()
                    }
                )
            )
        } else {
            error.value = validateOrder.second
        }
    }

    fun deleteItemOrdered(message: String, order: Order) {
        val listID = order.orderDetail.map { it.idBook }
        registerDisposable(
            executeTask(
                task = { cartEntityLocalDAO.deleteByIDs(listID) },
                onSuccess = {
                    _orderState.value = message
                },
                onError = {
                    error.value = it.toString()
                }
            )
        )
    }

    fun getMoneyOfShip(id: Int) {
        registerDisposable(
            executeTask(
                task = { shipRepository.getMoneyShip(id) },
                onSuccess = {
                    _moneyOfShip.value = it.data as? ShippingMethod
                },
                onError = {
                    _moneyOfShip.value = null
                }
            )
        )
    }

    private fun validateOrder(context: Context?, order: Order): Pair<Boolean, String?> {
        return if (order.address.isEmpty()) {
            Pair(false, context?.getString(R.string.text_field_addrees_empty))
        } else if (order.phone.isEmpty()) {
            Pair(false, context?.getString(R.string.text_field_phone))
        } else if (order.orderDetail.isEmpty()) {
            Pair(false, context?.getString(R.string.mess_bill_is_empty))
        } else if (order.receiver.isEmpty()) {
            Pair(false, context?.getString(R.string.text_field_receiver))
        } else {
            Pair(true, null)
        }
    }

    fun calcTotalPrice(list: List<OrderDetail>) {
        _totalPrice.value = list.sumOf { it.price * it.amount }
    }

    fun getLocationName(context: Context?, location: Location) {
        if (context == null)
            return
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(
                location.latitude,
                location.longitude, Constant.DEFAULT.FIST_RESULT
            )
            if (!addresses.isNullOrEmpty()) {
                val item = addresses.first()
                for (i in 0..item.maxAddressLineIndex) {
                    _locationName.value = item.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            val message = e.message
            _locationName.value = Constant.DEFAULT.STRING
        }
    }
}
