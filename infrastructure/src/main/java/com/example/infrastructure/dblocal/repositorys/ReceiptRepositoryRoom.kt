package com.example.infrastructure.dblocal.repositorys

import com.example.domain.aggregate.Receipt
import com.example.domain.repository.ReceiptRepository
import com.example.infrastructure.dblocal.daos.ReceiptDao
import com.example.infrastructure.dblocal.dtos.toDomainModel
import com.example.infrastructure.dblocal.dtos.toReceiptEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptRepositoryRoom @Inject constructor(
    private val receiptDao: ReceiptDao
) : ReceiptRepository {

    override fun getQuantityOfVehicles(typeVehicle: Int): Int {
        return receiptDao.getCountVehicle(typeVehicle)
    }

    override fun enterVehicle(receipt: Receipt): Long {
        return receiptDao.insertReceipt(receipt.toReceiptEntity())
    }

    override fun takeOutVehicle(receipt: Receipt): Int {
        return receiptDao.deleteReceipt(receipt.vehicle.plate)
    }

    override fun getVehicles(): List<Receipt> {
        return receiptDao.getVehicles().toDomainModel()
    }
}