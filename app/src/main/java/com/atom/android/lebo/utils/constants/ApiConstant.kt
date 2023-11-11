package com.atom.android.lebo.utils.constants

object ApiConstant {

    object TIMEOUT {
        const val CONNECT = 10L
        const val READ = 10L
        const val WRITE = 10L
    }

    object ENDPOINT {
        const val LOGIN = "login"
        const val USER = "user"
        const val GENRE = "genre"
        const val NOTIFICATION = "notification"
        const val BOOK = "book"
        const val BOOK_RELATE = "$BOOK/relate"
        const val CART = "$BOOK/cart"
        const val BANNER = "banner"
        const val AUTHOR = "author"
        const val CHECK_LOGIN = "$LOGIN/check_login"
        const val GENERATE_OTP = "$USER/generate_otp"
        const val CHANGE_PASSWORD = "$USER/change_password"
        const val REGISTER_NOTIFICATION = "$USER/register_notification"
        const val UNREGISTER_NOTIFICATION = "$USER/unregister_notification"
        const val FORGOT_PASSWORD = "forgot_password"
        const val ORDER = "order"
        const val ORDER_USER = "$ORDER/user"
        const val SHIPPING_METHOD = "shipping_method"
    }

    object FILED {
        const val EMAIL = "email"
        const val OLD_PASSWORD = "oldPassword"
        const val TOKEN = "token"
        const val NEW_PASSWORD = "newPassword"
        const val PAGE = "page"
        const val ID_BOOK = "idBook"
        const val ID = "id"
        const val URL = "url"
    }

    object TYPEOFBILL {
        const val PENDING = 1
        const val ACCEPT = 2
        const val DELIVERY = 3
        const val SUCCESS = 4
        const val DESTROY = 5
        const val LOST = 6
        const val ALL = 0
    }

    object SHIPPINGMETHOD {
        const val COD = 1
        const val PAYMENT = 2
    }

    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer"
}
