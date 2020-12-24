package com.example.domain.entity

import com.example.domain.aggregate.Receipt
import com.example.domain.exception.CanNotEnterVehicleException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ReceiptTest {

    @Test
    fun receipt_createReceiptWithMotorcycle() {

        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Motorcycle("BJO90F", 150)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle, true)

        //Assert
        assertNotNull(expected)
    }


    @Test
    fun receipt_createReceiptWithCar() {

        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Car("BJO90F")

        //Act
        val expected =
            Receipt(entryVehicle, vehicle, true)

        //Assert
        assertNotNull(expected)
    }

    @Test
    fun receipt_createReceiptWithCarOut() {

        //Arrange
        val entryVehicle = 1608818170116 // 24/12/2020 8 AM
        val vehicle = Car("BJO90F")

        //Act
        val expected =
            Receipt(entryVehicle, vehicle, false)
        expected.departureDate = 1608915389051 // 24/12/2020 8 AM + 27 hours

        //Assert
        assertEquals(11000.0, expected.amount)
    }

    @Test
    fun receipt_createReceiptWithMotorcycleOutCC650() {

        //Arrange
        val entryVehicle = 1608818273442 // 24/12/2020 8 AM
        val vehicle = Motorcycle("BJO90F", 650)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle, false)
        expected.departureDate = 1608854304993 // 24/12/2020 8 AM + 10 hours

        //Assert
        assertEquals(6000.0, expected.amount)
    }

    @Test
    fun receipt_createReceiptWithMotorcycleOutCC150() {

        //Arrange
        val entryVehicle = 1608818273442 // 24/12/2020 8 AM
        val vehicle = Motorcycle("BJO90F", 150)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle, false)
        expected.departureDate = 1608854304993 // 24/12/2020 8 AM + 10 hours

        //Assert
        assertEquals(4000.0, expected.amount)
    }

    @Test
    fun receipt_createReceiptWithMotorcyclePlateInitANotCanEntry() {

        //Arrange
        val entryVehicle = 1608818273442 // 24/12/2020 8 AM THU
        val vehicle = Motorcycle("AJO90F", 150)

        try {
            //Act
            Receipt(entryVehicle, vehicle, true)
        } catch (ex: CanNotEnterVehicleException) {
            //Assert
            assertEquals("No puede ingresar el vehiculo ya que no es domingo o lunes.", ex.message)
        }
    }

    @Test
    fun receipt_createReceiptWithMotorcyclePlateInitACanEntryMon() {

        //Arrange
        val entryVehicle = 1608559073442 // 21/12/2020 8 AM THU
        val vehicle = Motorcycle("AJO95F", 150)

        //Act
        val expected = Receipt(entryVehicle, vehicle, true)

        //Assert
        assertNotNull(expected)
    }

    @Test
    fun receipt_createReceiptWithMotorcyclePlateInitACanEntrySun() {

        //Arrange
        val entryVehicle = 1608472673442 // 20/12/2020 8 AM THU
        val vehicle = Motorcycle("AJO95F", 150)

        //Act
        val expected = Receipt(entryVehicle, vehicle, true)

        //Assert
        assertNotNull(expected)
    }

}