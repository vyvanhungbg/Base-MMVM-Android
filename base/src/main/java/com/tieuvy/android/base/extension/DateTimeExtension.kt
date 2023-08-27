package com.tieuvy.android.base.extension

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun Long.toDateTime(pattern: String = "dd/MM/yyyy HH:mm"): String {
    val formatter = SimpleDateFormat(pattern)
    return formatter.format(this)
}

fun Context.pickDateTime(action: (hour: Int, minute: Int) -> Unit) {
    Locale.setDefault(Locale("vi"))
    val currentDateTime = Calendar.getInstance()
    val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    val startMinute = currentDateTime.get(Calendar.MINUTE)
    TimePickerDialog(
        this,
        AlertDialog.THEME_HOLO_LIGHT,
        TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            action(
                hour,
                minute
            )
        },
        startHour,
        startMinute,
        true
    ).apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        show()
    }
}

fun Date.toDateTimeForHuman(
    textForToDay: String = "Today",
    textForYesterday: String = "Yesterday",
    pattern: String = "EEE dd-MM-yyyy 'at' HH:mm",
    locale: Locale = Locale("vi", "VN")
): String {
    try {
        val patternNotDayOfWeek = " dd-MM-yyyy 'at' HH:mm"
        val timeFormatter = SimpleDateFormat(patternNotDayOfWeek, locale)
        val today = Date()
        val yesterday = getYesterday()

        return if (this.isThisDay(today)) {
            "$textForToDay " + timeFormatter.format(this)
        } else if (this.isThisDay(yesterday)) {
            "$textForYesterday " + timeFormatter.format(this)
        } else {
            val dateFormat = SimpleDateFormat(pattern, locale)
            dateFormat.format(this).toString()
        }
    } catch (e: Exception) {
        return ""
    }
}


private fun getYesterday(): Date {
    return Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
}

private fun Date.isThisDay(
    date: Date?,
    patternCompare: String = "yyyy-MM-dd",
    locale: Locale = Locale("vi", "VN")
): Boolean {
    if(date == null )
        return false
    val timeCompare = SimpleDateFormat(patternCompare, locale)
    return timeCompare.format(date).equals(timeCompare.format(this))
}