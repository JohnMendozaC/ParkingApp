package com.example.infrastructure.dblocal.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Receipt"
)
data class ReceiptEntity(
    @PrimaryKey
    @ColumnInfo(name = "plate")
    val plate: String,
    val entryDate: Long,
    val type: Int
) {
    var departureDate: Long? = null
    var cylinderCapacity: Int? = null
}
