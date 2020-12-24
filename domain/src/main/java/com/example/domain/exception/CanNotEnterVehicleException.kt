package com.example.domain.exception

class CanNotEnterVehicleException(ERROR_CALCULATE_AMOUNT: String = "No puede ingresar el vehiculo ya que no es domingo o lunes.") :
    RuntimeException(ERROR_CALCULATE_AMOUNT)