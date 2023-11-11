package com.atom.android.lebo.utils.extensions

import com.atom.android.lebo.ui.cart.ACTION
import com.atom.android.lebo.utils.constants.Constant

fun actionCart(oldValue: Int, newValue: Int): ACTION {
    return if (oldValue == Constant.DEFAULT.ITEM_CART && newValue == Constant.DEFAULT.ITEM_CART.inc()) {
        ACTION.INSERT
    } else if (oldValue == Constant.DEFAULT.ITEM_CART.inc() && newValue == Constant.DEFAULT.ITEM_CART) {
        ACTION.DELETE
    } else if (oldValue < newValue) {
        ACTION.ASC
    } else {
        ACTION.DES
    }
}
