package com.example.parkingapp.dimodule

import android.content.Context
import com.example.infrastructure.dblocal.AppDataBase
import com.example.infrastructure.dblocal.daos.ReceiptDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase =
        AppDataBase.getInstance(context)

    @Provides
    fun provideReceiptDao(appDataBase: AppDataBase): ReceiptDao = appDataBase.receiptDao()
}