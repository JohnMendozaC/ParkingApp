package com.example.infrastructure.dblocal.dtos

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.enums.VehicleType
import com.example.domain.entity.Vehicle
import com.example.infrastructure.dblocal.entitys.ReceiptEntity

fun Receipt.toReceiptEntity(): ReceiptEntity {
    var cc: Int? = null
    val typeVehicle = when (this.vehicle) {
        is Car -> {
            VehicleType.CAR.value
        }
        is Motorcycle -> {
            cc = (this.vehicle as Motorcycle).cylinderCapacity
            VehicleType.MOTORCYCLE.value
        }
        else -> throw RuntimeException("No se puede definir el tipo de vehiculo")
    }

    return ReceiptEntity(
        this.vehicle.plate,
        entryDate,
        typeVehicle
    ).apply {
        this.cylinderCapacity = cc
    }
}

fun List<ReceiptEntity>.toDomainModel(): List<Receipt> {
    return map {
        val newVehicle: Vehicle = when (it.type) {
            VehicleType.CAR.value -> {
                Car(it.plate)
            }
            VehicleType.MOTORCYCLE.value -> {
                Motorcycle(it.plate, it.cylinderCapacity ?: 0)

            }
            else -> throw RuntimeException("No se puede definir el tipo de vehiculo")
        }
        Receipt(it.entryDate, newVehicle, false)
    }
}
