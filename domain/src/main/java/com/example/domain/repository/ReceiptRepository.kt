package com.example.domain.repository

import com.example.domain.aggregate.Receipt

interface ReceiptRepository {

    fun getQuantityOfVehicles(typeVehicle: Int): Int

    fun enterVehicle(receipt: Receipt): Long

    fun takeOutVehicle(receipt: Receipt): Int

    fun getVehicles(): List<Receipt>
}