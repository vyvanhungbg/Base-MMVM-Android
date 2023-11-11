package com.atom.android.lebo.utils.constants

object Constant {

    private const val PROJECT_NAME = "com.atom.android.lebo."

    object DIALOGCONFIG {
        const val MARGIN_Y = -170
    }

    object REGEX {
        const val NUMBER_PHONE = "([0-9]{9,10})\\b"
        const val EMAIL = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$"
        const val PASS = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        const val PASS_WORD = "^.{6,}$"
    }

    object DELAY {
        const val SPLASH_SCREEN = 1500L
        const val INPUT_TEXT = 500L
    }

    object Type {
        const val NETWORK = "NETWORK"
        const val HTTP = "HTTP"
        const val SERVER = "SERVER"
        const val UNEXPECTED = "UNEXPECTED"
    }

    object BUNDLED {
        const val EMAIL = "BUNDLED_EMAIL"
        const val BOOK = "bundle_book"
        const val BILL = "bundle_id"
        const val ID = "id"
        const val BILL_NOTIFICATION = "BUNDLE_BILL_NOTIFICATION"
        const val ERROR = "-1"
    }

    object DEFAULT {
        const val POSITION = 0
        const val STRING = ""
        const val LENGTH_OTP = 6
        const val FIRST_PAGE = 1
        const val FIRST_ACTION = 1L
        const val DOT = '.'
        const val TYPE_SHARE = "text/plain"
        const val ROW_EFFECT = 1
        const val INSERT_FAILED = -1L
        const val ITEM_CART = 0
        const val AMOUNT_NOTIFICATION = 0
        const val MONEY_OF_SHIP = 0.0
        const val PRICE = 0.0
        const val ID_SHIPPING_METHOD = 1
        const val FIST_RESULT = 1
        const val ERROR_INDEX = -1
        const val QUEUEBACKSTACK = 2
    }

    object ACTION {
        const val DELETE = 0
        const val INSERT = 1
        const val DESCENDING = 3
        const val ASCENDING = 4
    }

    object LOCATION {
        const val MIN_DISTANCE = 0F
        const val TIME_UPDATE = 1000L
    }

    object NOTIFICATION {
        const val CHANNEL_ID = "CHANNEL_1"
        const val CHANNEL_NAME = "LEBO"
        const val REQUEST_CODE = 0
    }

    const val SLASH = " | "
    const val SIZE_DEFAULT_IMAGE = 120
    const val CURRENCY_UNIT = "đ"

    const val SIZE_SPAN_OF_TEXT = 1.2f
    const val SIZE_SPAN_OF_NUMBER = 0.8f

    const val SECOND_TO_MILLI = 1000L

    const val EXPAND_MAX_LINE = 10
    const val COLLAPSE_MAX_LINE = 2

    const val FORMAT_DATE_TIME = "dd-MM-yyyy '|' HH:mm:ss"
    const val FORMAT_DATE_TIME_INPUT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val TIMEZONE = "UTC"

    const val SHARED_PREF_ROOT = PROJECT_NAME + "SHARED_PREF"
    const val SHARED_PREF_DEFAULT_STRING = ""
    const val SHARED_PREF_TOKEN_LOGIN = PROJECT_NAME + "TOKEN_LOGIN"
    const val SHARED_PREF_TOKEN_MESSAGE = PROJECT_NAME + "TOKEN_MESSAGE"
    const val SHARED_PREF_EMAIL = PROJECT_NAME + "EMAIL"
}
