package com.example.domain.entity

import com.example.domain.aggregate.Receipt
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
        val entryVehicle = System.currentTimeMillis() //- (24 * 3600000)
        val vehicle = Motorcycle("BJO90F", 150)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle)

        //Assert
        assertNotNull(expected)
    }


    @Test
    fun receipt_createReceiptWithCar() {

        //Arrange
        val entryVehicle = System.currentTimeMillis() //- (24 * 3600000)
        val vehicle = Car("BJO90F")

        //Act
        val expected =
            Receipt(entryVehicle, vehicle)

        //Assert
        assertNotNull(expected)
    }

    @Test
    fun receipt_createReceiptWithCarOut() {

        //Arrange
        val entryVehicle = System.currentTimeMillis() //+ (24 * 3600000)
        val vehicle = Car("BJO90F")

        //Act
        val expected =
            Receipt(entryVehicle, vehicle)
        expected.departureDate = entryVehicle + (27 * 3600000)

        //Assert
        assertEquals(11000.0, expected.amount)
    }

    @Test
    fun receipt_createReceiptWithMotorcycleOutCC650() {

        //Arrange
        val entryVehicle = System.currentTimeMillis() //+ (24 * 3600000)
        val vehicle = Motorcycle("BJO90F", 650)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle)
        expected.departureDate = entryVehicle + (10 * 3600000)

        //Assert
        assertEquals(6000.0, expected.amount)
    }

    @Test
    fun receipt_createReceiptWithMotorcycleOutCC150() {

        //Arrange
        val entryVehicle = System.currentTimeMillis() //+ (24 * 3600000)
        val vehicle = Motorcycle("BJO90F", 150)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle)
        expected.departureDate = entryVehicle + (10 * 3600000)

        //Assert
        assertEquals(4000.0, expected.amount)
    }

    @Test
    fun receipt_createReceiptWithMotorcyclePlateInitA() {

        //Arrange
        val entryVehicle = System.currentTimeMillis() //- (24 * 3600000)
        val vehicle = Motorcycle("AJO90F", 150)

        //Act
        val expected =
            Receipt(entryVehicle, vehicle)

        //Assert
        assertNotNull(expected)
    }


}