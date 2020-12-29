package com.example.parkingapp.viewmodels

import android.database.sqlite.SQLiteConstraintException
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.aggregate.Receipt
import com.example.domain.exception.CalculateAmountException
import com.example.domain.exception.CanNotEnterVehicleException
import com.example.domain.exception.MaximumCantVehicleException
import com.example.domain.service.ReceiptService
import com.example.domain.entity.Vehicle
import com.example.infrastructure.dblocal.utils.response.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.coroutineContext

class ReceiptViewModel @ViewModelInject constructor(
    private val receiptService: ReceiptService
) : ViewModel() {

    fun enterVehicle(entryDate: Long, vehicle: Vehicle): LiveData<Resource<String>> {
        val flow = flow {
            emit(Resource.success(receiptService.enterVehicle(entryDate, vehicle)))
        }
        return flow
            .onStart { emit(Resource.loading(null, "Loading from database...")) }
            .catch { exception ->
                with(exception) {
                    val msg = when (this) {
                        is SQLiteConstraintException -> "El vehiculo ya se encuentra en el parqueadero."
                        is MaximumCantVehicleException, is CanNotEnterVehicleException -> message
                        else -> "Oh oh ocurrio algo inesperado!"
                    }
                    emit(Resource.error(null, 0, msg))
                }
            }
            .flowOn(Dispatchers.IO)
            .asLiveData()
    }

    fun takeOutVehicle(departureDate: Long, receipt: Receipt): LiveData<Resource<Double>> {
        val flow = flow {
            emit(Resource.success(receiptService.takeOutVehicle(departureDate, receipt)))
        }
        return flow
            .onStart { emit(Resource.loading(null, "Loading from database...")) }
            .catch { exception ->
                with(exception) {
                    val msg = when (this) {
                        is CalculateAmountException -> message
                        else -> "Oh oh ocurrio algo inesperado!"
                    }
                    emit(Resource.error(null, 0, msg))
                }
            }
            .flowOn(Dispatchers.IO)
            .asLiveData()
    }

    fun getVehicles(): LiveData<Resource<List<Receipt>>> {
        val flow = flow {
            emit(Resource.success(receiptService.getVehicles()))
        }
        return flow
            .onStart { emit(Resource.loading(null, "Loading from database...")) }
            .catch {
                emit(Resource.error(null, 0, "Oh oh ocurrio algo inesperado!"))
            }
            .flowOn(Dispatchers.IO)
            .asLiveData()
    }
}
