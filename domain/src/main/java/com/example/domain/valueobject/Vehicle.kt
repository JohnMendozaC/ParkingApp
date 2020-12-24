package com.example.domain.valueobject

abstract class Vehicle (var plate: String){
    fun plateIsInitA(): Boolean {
        return (plate[0].equals('A', true))
    }
}