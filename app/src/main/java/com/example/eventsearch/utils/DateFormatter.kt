package com.example.eventsearch.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.toDate(
    dateFormat: String = "yyyy-MM-dd",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date? {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault()).also {
        it.timeZone = timeZone
    }
    return parser.parse(this)
}

fun Date.formatToReadableDate(
    dateFormat: String = "MMM dd yyyy",
    timeZone: TimeZone = TimeZone.getDefault()
): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault()).also {
        it.timeZone = timeZone
    }
    return formatter.format(this)
}
