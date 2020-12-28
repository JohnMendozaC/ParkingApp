package com.example.domain.service

import android.database.sqlite.SQLiteConstraintException
import com.example.domain.aggregate.Receipt
import com.example.domain.repository.ReceiptRepository
import com.example.domain.util.Resource
import com.example.domain.valueobject.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ReceiptService @Inject constructor(private val receiptRepository: ReceiptRepository) {

    @ExperimentalCoroutinesApi
    fun enterVehicle(entryDate: Long, vehicle: Vehicle): Flow<Resource<String>> {
        val flow = flow {
            val receipt = Receipt(entryDate, vehicle, true)
            if (receiptRepository.isSpaceForVehicle(vehicle)) {
                emit(Resource.loading(null, "Saving data in database..."))
                receiptRepository.enterVehicle(receipt).collect { response ->
                    if (response > 0L) {
                        emit(Resource.success("Ok",200,"¡Se guardo el vehiculo con exito!"))
                    } else {
                        emit(Resource.error(null, 0, "Oh oh ocurrio algo inesperado!"))
                    }
                }
            } else {
                emit(Resource.error(null, 0, "No hay cupo para guardar el carro."))
            }
        }

        return flow
            .onStart { emit(Resource.loading(null, "Loading from database...")) }
            .catch { exception ->
                with(exception) {
                    val msg = when (this) {
                        is SQLiteConstraintException -> "El vehiculo ya se encuentra en el parqueadero."
                        else -> /*"Oh oh ocurrio algo inesperado!"*/message
                    }
                    emit(Resource.error(null, 0, msg))
                }
            }
            .flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    fun takeOutVehicle(departureDate: Long, receipt: Receipt): Flow<Resource<Double>> {
        val flow = flow {
            emit(Resource.loading(null, "Deleting data in database..."))
            receiptRepository.takeOutVehicle(receipt).collect { response ->
                if (response > 0) {
                    receipt.departureDate = departureDate
                    emit(Resource.success(receipt.amount))
                } else {
                    emit(Resource.error(null, 0, "Oh oh ocurrio algo inesperado!"))
                }
            }
        }
        return flow
            .onStart { emit(Resource.loading(null, "Loading from database...")) }
            .catch {
                emit(Resource.error(null, 0, "Oh oh ocurrio algo inesperado!"))
            }
            .flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    fun getVehicles(): Flow<Resource<List<Receipt>>> {
        val flow = flow {
            emit(Resource.loading(null, "Looking data in database..."))
            emit(Resource.success(receiptRepository.getVehicles()))
        }
        return flow
            .onStart { emit(Resource.loading(null, "Loading from database...")) }
            .catch { exception ->
                emit(Resource.error(null, 0, "Oh oh ocurrio algo inesperado!"))
            }
            .flowOn(Dispatchers.IO)
    }

}