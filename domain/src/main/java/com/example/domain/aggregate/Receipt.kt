package com.example.domain.aggregate

import com.example.domain.entity.Motorcycle
import com.example.domain.enum.Parking
import com.example.domain.enum.Prices
import com.example.domain.enum.Time
import com.example.domain.exception.CalculateAmountException
import com.example.domain.exception.CanNotEnterVehicleException
import com.example.domain.util.ConvertDate
import com.example.domain.util.ConvertDate.convertLongToTime
import com.example.domain.valueobject.Vehicle
import java.io.Serializable
import java.util.*

class Receipt(newEntryDate: Long, newVehicle: Vehicle, validatePlate: Boolean) : Serializable {

    var vehicle: Vehicle = newVehicle
        private set
    var entryDate: Long = newEntryDate
        private set
    var amount: Double? = null
        private set
    var departureDate: Long = 0
        set(value) {
            field = value
            calculateAmount()
        }

    init {
        if (newVehicle.plateIsInitA() && validatePlate) {
            val c = Calendar.getInstance()
            c.timeInMillis = newEntryDate
            val day = c.get(Calendar.DAY_OF_WEEK)
            if (day != Calendar.SUNDAY && day != Calendar.MONDAY) {
                throw CanNotEnterVehicleException()
            }
        }
    }

    private fun calculateAmount() {
        amount = when (vehicle) {
            is com.example.domain.entity.Car -> calculateAmountCar()
            is Motorcycle -> calculateAmountMotorcycle(vehicle as Motorcycle)
            else -> throw CalculateAmountException()
        }
    }

    private fun calculateAmountCar(): Double {
        return calculateAmountDependentDayOrHour(Prices.CAR.hour, Prices.CAR.day)
    }

    private fun calculateAmountMotorcycle(motorcycle: Motorcycle): Double {
        var amount = calculateAmountDependentDayOrHour(
            Prices.MOTORCYCLE.hour,
            Prices.MOTORCYCLE.day
        )
        if (motorcycle.cylinderCapacity > Parking.MAX_CYLINDER_MOTORCYCLE.value)
            amount += Prices.MOTORCYCLE.additionalAmount
        return amount
    }

    private fun calculateAmountDependentDayOrHour(priceHour: Double, priceDay: Double): Double {
        val hours = ConvertDate.getCheckInAndCheckOutTimes(entryDate, departureDate)
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

    fun getEntryDateS() = entryDate.convertLongToTime()

    fun getDepartureDateS() = departureDate.convertLongToTime()
}