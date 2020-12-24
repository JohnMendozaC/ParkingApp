package com.example.domain.util

import com.example.domain.enum.Time

object ConvertDate {

    fun getCheckInAndCheckOutTimes(checkIn: Long, checkOut: Long): Int {
        val result = checkOut - checkIn
        return (result / Time.MILLS_HOUR.value).toInt()
    }
}