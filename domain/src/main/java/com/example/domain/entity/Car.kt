package com.example.domain.entity

import com.example.domain.enums.Prices

class Car(plate: String) : Vehicle(plate) {

    fun calculateAmountCar(hours: Int): Double {
        return calculateAmountDependentDayOrHour(Prices.CAR.hour, Prices.CAR.day, hours)
    }
}