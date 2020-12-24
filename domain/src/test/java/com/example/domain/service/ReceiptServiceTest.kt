package com.example.domain.service

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Motorcycle
import com.example.domain.repository.ReceiptRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReceiptServiceTest {

    @Mock
    lateinit var receiptRepository: ReceiptRepository

    @Test
    fun enterMotorcycle_motorcycleWithCorrectParameters_successful() {

        //Arrange
        val entryVehicle = System.currentTimeMillis() //- (24 * 3600000)
        val vehicle = Motorcycle("BJO90F", 150)
//        val motorcycle = Receipt(entryVehicle, vehicle)

        //Act
        receiptRepository.enterMotorcycle(vehicle)
    }

    @Test
    fun enterCar_carWithCorrectIsNotSpace_successful() {
        //Arrange

        //Act

        //Assert

    }
}