package com.atom.android.lebo.utils.extensions

import android.content.Context
import com.atom.android.lebo.R
import com.atom.android.lebo.utils.constants.Constant
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.regex.Pattern

fun String?.isValidEmail(context: Context?) = when {
    this.isNullOrEmpty() -> {
        Pair(false, context?.getString(R.string.mess_required_field_email))
    }
    this.isBlank() -> {
        Pair(false, context?.getString(R.string.mess_field_blank))
    }
    !Pattern.matches(Constant.REGEX.EMAIL, this) -> {
        Pair(false, context?.getString(R.string.mess_validate_email_failed))
    }
    else -> {
        Pair(true, null)
    }
}

fun String?.isValidPassword(context: Context?) = when {
    this.isNullOrEmpty() -> {
        Pair(false, context?.getString(R.string.mess_required_field_password))
    }
    this.isBlank() -> {
        Pair(false, context?.getString(R.string.mess_field_blank))
    }
    !this.matches(Constant.REGEX.PASS_WORD.toRegex()) -> {
        Pair(false, context?.getString(R.string.mess_validate_password_failed))
    }
    else -> {
        Pair(true, null)
    }
}

fun String?.isValidConfirmPassword(context: Context?, password: String) = when {
    this.isNullOrEmpty() -> {
        Pair(false, context?.getString(R.string.mess_required_field_confirm_password))
    }
    this.isBlank() -> {
        Pair(false, context?.getString(R.string.mess_field_blank))
    }
    this != password -> {
        Pair(false, context?.getString(R.string.mess_validate_confirm_password_failed))
    }
    else -> {
        Pair(true, null)
    }
}

fun String?.isValidOTP(context: Context?) = when {
    this.isNullOrEmpty() -> {
        Pair(false, context?.getString(R.string.mess_required_field_otp))
    }
    this.length != Constant.DEFAULT.LENGTH_OTP -> {
        Pair(false, context?.getString(R.string.mess_validate_otp_failed))
    }
    else -> {
        Pair(true, null)
    }
}

fun String.convertStrToMoney(): String {
    return try {
        val nf: NumberFormat = NumberFormat.getCurrencyInstance()
        val dfs = DecimalFormatSymbols()
        dfs.currencySymbol = Constant.DEFAULT.STRING
        dfs.groupingSeparator = Constant.DEFAULT.DOT
        nf.minimumFractionDigits = 0
        nf.roundingMode = RoundingMode.HALF_UP
        (nf as DecimalFormat).decimalFormatSymbols = dfs
        (nf.format(BigDecimal(this))) + Constant.CURRENCY_UNIT
    } catch (ex: NumberFormatException) {
        val message = ex.message
        Constant.DEFAULT.STRING
    }
}

fun String?.convertInstantToDate(): String {
    if (this == null || this.isEmpty()) return Constant.DEFAULT.STRING
    return try {
        val inputFormat = SimpleDateFormat(Constant.FORMAT_DATE_TIME_INPUT) // UTC time
        val outputFormat = SimpleDateFormat(Constant.FORMAT_DATE_TIME) // local time
        inputFormat.timeZone = TimeZone.getTimeZone(Constant.TIMEZONE)
        val date = inputFormat.parse(this)
        outputFormat.format(date).toString()
    } catch (ex: ParseException) {
        Constant.DEFAULT.STRING
    }
}
