package com.example.infrastructure.dblocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.infrastructure.dblocal.daos.ReceiptDao
import com.example.infrastructure.dblocal.entitys.ReceiptEntity
import com.example.infrastructure.dblocal.utils.DATABASE_NAME

@Database(
    entities = [ReceiptEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao

    companion object {

        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}