package com.example.domain.exception

class CalculateAmountException(ERROR_CALCULATE_AMOUNT: String = "No se pudo obtener el monto.") :
    RuntimeException(ERROR_CALCULATE_AMOUNT)
