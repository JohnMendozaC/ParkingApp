package com.example.domain.repository

import com.example.domain.aggregate.Receipt
import com.example.domain.valueobject.Vehicle

interface ReceiptRepository {

    fun isSpaceForVehicle(vehicle: Vehicle): Boolean

    fun enterVehicle(receipt: Receipt): Long

    fun takeOutVehicle(receipt: Receipt): Int

    fun getVehicles(): List<Receipt>
}