package com.example.parkingapp.adapters

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.aggregate.Receipt
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.enum.Parking
import com.example.domain.valueobject.Vehicle
import com.example.parkingapp.R

@BindingAdapter("isVisible")
fun bindIsVisible(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("setDataVehicle")
fun setDataVehicles(recycler: RecyclerView, data: List<Receipt>?) {
    data?.also {
        (recycler.adapter as VehicleAdapter).submitList(it)
    }
}

@BindingAdapter("changeImageVehicle")
fun changeImageVehicle(imgView: AppCompatImageView, vehicle: Vehicle?) {
    vehicle?.also {
        val img = when (vehicle) {
            is Car -> R.drawable.ic_car
            is Motorcycle -> {
                if (vehicle.cylinderCapacity > Parking.MAX_CYLINDER_MOTORCYCLE.value)
                    R.drawable.ic_moto500cc
                else
                    R.drawable.ic_scooter
            }
            else -> R.drawable.ic_bicycle
        }
        imgView.setImageDrawable(ContextCompat.getDrawable(imgView.context, img))
    }
}

