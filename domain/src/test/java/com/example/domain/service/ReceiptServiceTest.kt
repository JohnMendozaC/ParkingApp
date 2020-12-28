package com.example.domain.service

import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.repository.ReceiptRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        GlobalScope.launch(Dispatchers.IO) {
            receiptRepository.enterVehicle(motorcycle).collect {
                //Assert
                val expected = (it > 0)
                assert(expected)
            }
        }
    }

    @Test
    fun enterCar_carWithCorrectParameters_successful() {
        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Car("BJO88F")
        val motorcycle = Receipt(entryVehicle, vehicle, true)

        //Act
        GlobalScope.launch(Dispatchers.IO) {
            receiptRepository.enterVehicle(motorcycle).collect {
                //Assert
                val expected = (it > 0)
                assert(expected)
            }
        }
    }

    @Test
    fun takeOut_vehicleWithCorrectIsNotSpace_successful() {
        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Car("BJO88F")
        val motorcycle = Receipt(entryVehicle, vehicle, true)
        motorcycle.departureDate = 1608915389051 // 24/12/2020 8 AM + 27 hours

        //Act
        GlobalScope.launch(Dispatchers.IO) {
            receiptRepository.takeOutVehicle(motorcycle).collect {
                //Assert
                val expected = (it > 0)
                assert(expected)
            }
        }
    }
}