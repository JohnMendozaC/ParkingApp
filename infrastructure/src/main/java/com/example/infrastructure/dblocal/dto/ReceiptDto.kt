package com.example.infrastructure.dblocal.dto

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.infrastructure.dblocal.entitys.ReceiptEntity
import com.example.infrastructure.dblocal.enums.VehicleType

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
        this.vehicle?.plate ?: run { throw RuntimeException("El vehiculo no tiene placa") },
        entryDate,
        typeVehicle
    ).apply {
        this.cylinderCapacity = cc
    }
}
