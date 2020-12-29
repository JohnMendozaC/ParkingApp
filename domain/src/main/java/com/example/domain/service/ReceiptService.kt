package com.example.domain.service

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.enums.Parking
import com.example.domain.enums.VehicleType
import com.example.domain.exception.CalculateAmountException
import com.example.domain.exception.MaximumCantVehicleException
import com.example.domain.repository.ReceiptRepository
import com.example.domain.entity.Vehicle
import javax.inject.Inject

class ReceiptService @Inject constructor(private val receiptRepository: ReceiptRepository) {

    private fun isSpaceForVehicle(vehicle: Vehicle): Boolean {
        return when (vehicle) {
            is Car -> (receiptRepository.getQuantityOfVehicles(VehicleType.CAR.value) < Parking.MAX_CANT_CAR.value)
            is Motorcycle -> (receiptRepository.getQuantityOfVehicles(VehicleType.MOTORCYCLE.value) < Parking.MAX_CANT_MOTORCYCLE.value)
            else -> throw RuntimeException("Calculate space vehicle")
        }
    }

    fun enterVehicle(entryDate: Long, vehicle: Vehicle): String {
        val receipt = Receipt(entryDate, vehicle, true)
        if (isSpaceForVehicle(vehicle)) {
            if (receiptRepository.enterVehicle(receipt) > 0) {
                return "¡Se guardo el vehiculo con exito!"
            } else {
                throw RuntimeException()
            }
        } else {
            throw MaximumCantVehicleException()
        }
    }

    fun takeOutVehicle(departureDate: Long, receipt: Receipt): Double {
        if (receiptRepository.takeOutVehicle(receipt) > 0) {
            receipt.departureDate = departureDate
            return receipt.amount ?: run { throw CalculateAmountException() }
        } else {
            throw CalculateAmountException()
        }
    }

    fun getVehicles(): List<Receipt> {
        return receiptRepository.getVehicles()
    }
}