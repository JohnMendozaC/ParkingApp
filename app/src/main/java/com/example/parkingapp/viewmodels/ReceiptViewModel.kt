package com.example.parkingapp.viewmodels

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Vehicle
import com.example.domain.exception.CalculateAmountException
import com.example.domain.exception.CanNotEnterVehicleException
import com.example.domain.exception.MaximumCantVehicleException
import com.example.domain.service.ReceiptService
import com.example.infrastructure.dblocal.utils.response.Resource
import com.example.parkingapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class ReceiptViewModel @ViewModelInject constructor(
    @ApplicationContext private val app: Context,
    private val receiptService: ReceiptService
) : ViewModel() {

    fun enterVehicle(entryDate: Long, vehicle: Vehicle): LiveData<Resource<String>> {
        val flow = flow {
            emit(Resource.success(receiptService.enterVehicle(entryDate, vehicle)))
        }
        return flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                with(exception) {
                    val msg = when (this) {
                        is SQLiteConstraintException -> app.getString(R.string.vehicle_already_in_the_parking_lot)
                        is MaximumCantVehicleException, is CanNotEnterVehicleException -> message
                        else -> app.getString(R.string.something_unexpected_happened)
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
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                with(exception) {
                    val msg = when (this) {
                        is CalculateAmountException -> message
                        else -> app.getString(R.string.something_unexpected_happened)
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
            .onStart { emit(Resource.loading(null, null)) }
            .catch {
                emit(Resource.error(null, 0, app.getString(R.string.something_unexpected_happened)))
            }
            .flowOn(Dispatchers.IO)
            .asLiveData()
    }
}
