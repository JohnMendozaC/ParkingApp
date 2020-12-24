package com.example.parkingapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.service.ReceiptService
import com.example.domain.util.Resource
import com.example.domain.valueobject.Vehicle

class ReceiptViewModel @ViewModelInject constructor(
    private val receiptService: ReceiptService
) : ViewModel() {

    fun enterVehicle(entryDate: Long, vehicle: Vehicle): LiveData<Resource<String>> =
        receiptService.enterVehicle(entryDate, vehicle).asLiveData()

    fun enterVehicle(entryDate: Long, vehicle: Vehicle): LiveData<Resource<String>> =
        receiptService.enterVehicle(entryDate, vehicle).asLiveData()
}