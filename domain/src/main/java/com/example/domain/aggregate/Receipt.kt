package com.example.domain.aggregate

import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.exception.CalculateAmountException
import com.example.domain.exception.CanNotEnterVehicleException
import com.example.domain.util.ConvertDate
import com.example.domain.util.ConvertDate.convertLongToTime
import com.example.domain.entity.Vehicle
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
        val hours = ConvertDate.getCheckInAndCheckOutTimes(entryDate, departureDate)
        amount = when (vehicle) {
            is Car -> (vehicle as Car).calculateAmountCar(hours)
            is Motorcycle -> (vehicle as Motorcycle).calculateAmountMotorcycle(hours)
            else -> throw CalculateAmountException()
        }
    }

    fun getEntryDateString() = entryDate.convertLongToTime()

    fun getDepartureDateString() = departureDate.convertLongToTime()
}