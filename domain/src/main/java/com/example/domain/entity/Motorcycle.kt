package com.example.domain.entity

import com.example.domain.enums.Parking
import com.example.domain.enums.Prices

class Motorcycle(plate: String, var cylinderCapacity: Int) : Vehicle(plate) {

    fun calculateAmountMotorcycle( hours: Int): Double {
        var amount = calculateAmountDependentDayOrHour(
            Prices.MOTORCYCLE.hour,
            Prices.MOTORCYCLE.day,
            hours
        )
        if (cylinderCapacity > Parking.MAX_CYLINDER_MOTORCYCLE.value)
            amount += Prices.MOTORCYCLE.additionalAmount
        return amount
    }
}
