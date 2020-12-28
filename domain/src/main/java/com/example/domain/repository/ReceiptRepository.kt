package com.example.domain.repository

import com.example.domain.aggregate.Receipt
import com.example.domain.valueobject.Vehicle
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {

    fun isSpaceForVehicle(vehicle: Vehicle): Boolean

    fun enterVehicle(receipt: Receipt): Flow<Long>

    fun takeOutVehicle(receipt: Receipt): Flow<Int>

    fun getVehicles(): List<Receipt>
}