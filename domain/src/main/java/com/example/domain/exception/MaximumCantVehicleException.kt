package com.example.domain.exception

class MaximumCantVehicleException(ERROR_CANT_CAR: String = "No hay cupo para guardar el vehiculo.") :
    RuntimeException(ERROR_CANT_CAR)
