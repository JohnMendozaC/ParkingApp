package com.example.domain.valueobject

import java.io.Serializable

abstract class Vehicle(var plate: String) : Serializable {
    fun plateIsInitA(): Boolean {
        return (plate[0].equals('A', true))
    }
}