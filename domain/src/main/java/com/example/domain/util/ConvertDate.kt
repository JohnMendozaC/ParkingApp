package com.example.domain.util

import com.example.domain.enums.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ConvertDate {

    fun getCheckInAndCheckOutTimes(checkIn: Long, checkOut: Long): Int {
        val result = checkOut - checkIn
        return (result / Time.MILLS_HOUR.value).toInt()
    }


    fun Long.convertLongToTime(): String {
        val date = Date(this)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun String.getLongDate(): Long =
        try {
            val d: Date? = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(this)
            d?.time ?: 0
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }

}