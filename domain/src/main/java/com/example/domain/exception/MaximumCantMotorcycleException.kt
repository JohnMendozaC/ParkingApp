package com.example.domain.exception

class MaximumCantMotorcycleException(ERROR_CANT_MOTORCYCLE: String = "No hay cupo para guardar la moto.") :
    RuntimeException(ERROR_CANT_MOTORCYCLE)