package com.example.parkingapp.dimodule

import com.example.domain.repository.ReceiptRepository
import com.example.infrastructure.dblocal.repository.ReceiptRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
abstract class ReceiptModule {

    @Binds
    abstract fun bindReceiptRepository(receiptRepositoryImpl: ReceiptRepositoryImpl): ReceiptRepository
}