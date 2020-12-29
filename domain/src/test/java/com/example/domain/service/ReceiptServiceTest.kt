package com.example.domain.service

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
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
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Motorcycle("BJO90F", 150)
        val motorcycle = Receipt(entryVehicle, vehicle, true)

        //Act
        val expected = (receiptRepository.enterVehicle(motorcycle) >= 0)

        //Assert
        assert(expected)
    }

    @Test
    fun enterCar_carWithCorrectParameters_successful() {
        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Car("BJO88F")
        val motorcycle = Receipt(entryVehicle, vehicle, true)

        //Act
        val expected = (receiptRepository.enterVehicle(motorcycle) >= 0)

        //Assert
        assert(expected)
    }

    @Test
    fun takeOut_vehicleWithCorrectIsNotSpace_successful() {
        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Car("BJO88F")
        val motorcycle = Receipt(entryVehicle, vehicle, true)
        motorcycle.departureDate = 1608915389051 // 24/12/2020 8 AM + 27 hours

        //Act
        val expected = (receiptRepository.takeOutVehicle(motorcycle) >= 0)

        //Assert
        assert(expected)
    }
}