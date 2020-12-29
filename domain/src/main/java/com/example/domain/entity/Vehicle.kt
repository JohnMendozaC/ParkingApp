package com.example.domain.entity

import com.example.domain.enums.Time
import java.io.Serializable

abstract class Vehicle(var plate: String) : Serializable {
    fun plateIsInitA(): Boolean {
        return (plate[0].equals('A', true))
    }

    protected fun calculateAmountDependentDayOrHour(
        priceHour: Double,
        priceDay: Double,
        hours: Int
    ): Double {
        return when {
            (hours < Time.MAX_HOUR.value) -> {
                hours * priceHour
            }
            (hours in Time.MAX_HOUR.value..Time.DAY_HOUR.value) -> {
                priceDay
            }
            else -> {
                val result = (hours / Time.DAY_HOUR.value.toFloat())
                val days = result.toInt()
                val newHours = (result.dec() * Time.DAY_HOUR.value)
                (days * priceDay) + (newHours * priceHour)
            }
        }
    }
}