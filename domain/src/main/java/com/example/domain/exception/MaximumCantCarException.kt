package com.example.domain.exception

class MaximumCantCarException(ERROR_CANT_CAR: String = "No hay cupo para guardar el carro.") :
    RuntimeException(ERROR_CANT_CAR)
