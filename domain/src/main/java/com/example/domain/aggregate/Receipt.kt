package com.example.domain.aggregate

import com.example.domain.entity.Motorcycle
import com.example.domain.enum.Parking
import com.example.domain.enum.Prices
import com.example.domain.enum.Time
import com.example.domain.exception.CalculateAmountException
import com.example.domain.exception.CanNotEnterVehicleException
import com.example.domain.util.ConvertDate
import com.example.domain.valueobject.Vehicle
import java.util.*

class Receipt {

    var vehicle: Vehicle? = null
        private set
    var entryDate: Long = 0
        private set
    var departureDate: Long = 0
        private set
    var amount: Double? = null
        private set

    constructor(newEntryDate: Long, newVehicle: Vehicle) {
        if (newVehicle.plateIsInitA()) {
            val c = Calendar.getInstance()
            c.timeInMillis = newEntryDate
            val day = c.get(Calendar.DAY_OF_WEEK)
            if (day != Calendar.SUNDAY && day != Calendar.MONDAY) {
                throw CanNotEnterVehicleException()
            }
        }
        entryDate = newEntryDate
        vehicle = newVehicle
    }

    constructor(newEntryDate: Long, newDepartureDate: Long, newVehicle: Vehicle) {
        entryDate = newEntryDate
        vehicle = newVehicle
        departureDate = newDepartureDate
        calculateAmount()
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
                val result = (hours / Time.MAX_HOUR.value.toFloat())
                val days = result.toInt()
                val newHours = (result.dec() * Time.DAY_HOUR.value)
                (days * priceDay) + (newHours * priceHour)
            }
        }
    }
}

