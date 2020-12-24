package com.example.infrastructure.dblocal.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.dblocal.entitys.ReceiptEntity

@Dao
interface ReceiptDao {

    @Query("SELECT COUNT(*) FROM receipt WHERE type = :type")
    fun getCountVehicle(type: Int): Int

    @Insert
    fun insertReceipt(receiptEntity: ReceiptEntity): Long

    @Delete
    fun deleteReceipt(receiptEntity: ReceiptEntity): Int
}