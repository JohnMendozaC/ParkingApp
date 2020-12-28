package com.example.infrastructure.dblocal.repository

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.enum.Parking
import com.example.domain.repository.ReceiptRepository
import com.example.infrastructure.dblocal.daos.ReceiptDao
import com.example.infrastructure.dblocal.dto.toDomainModel
import com.example.infrastructure.dblocal.dto.toReceiptEntity
import com.example.infrastructure.dblocal.enums.VehicleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptRepositoryImpl @Inject constructor(
    private val receiptDao: ReceiptDao
) : ReceiptRepository {

    override fun isSpaceForVehicle(vehicle: com.example.domain.valueobject.Vehicle): Boolean {
        return when (vehicle) {
            is Car -> (receiptDao.getCountVehicle(VehicleType.CAR.value) < Parking.MAX_CANT_CAR.value)
            is Motorcycle -> (receiptDao.getCountVehicle(VehicleType.MOTORCYCLE.value) < Parking.MAX_CANT_MOTORCYCLE.value)
            else -> throw RuntimeException("Calculate space vehicle")
        }
    }

    override fun enterVehicle(receipt: Receipt): Flow<Long> {
        return flow { emit(receiptDao.insertReceipt(receipt.toReceiptEntity())) }
    }

    override fun takeOutVehicle(receipt: Receipt): Flow<Int> {
        return flow { emit(receiptDao.deleteReceipt(receipt.vehicle.plate)) }
    }

    override fun getVehicles(): List<Receipt> {
        return receiptDao.getVehicles().toDomainModel()
    }
}