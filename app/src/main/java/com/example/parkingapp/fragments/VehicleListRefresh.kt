package com.example.parkingapp.fragments

import java.io.Serializable

interface VehicleListRefresh : Serializable {
    fun refreshList()
}