package com.example.parkingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.aggregate.Receipt
import com.example.parkingapp.R
import com.example.parkingapp.databinding.VehicleListItemBinding
import com.example.parkingapp.fragments.VehicleListRefresh

class VehicleAdapter(private val refreshList: VehicleListRefresh) :
    ListAdapter<Receipt, RecyclerView.ViewHolder>(VehicleDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VehicleViewHolder(
            VehicleListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receipt = getItem(position)
        (holder as VehicleViewHolder).bind(receipt)
    }

    inner class VehicleViewHolder(private val binding: VehicleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: Receipt) {
            binding.receipt = vehicle
            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(
                        R.id.action_vehicleListFragment_to_outVehicleDialogFragment,
                        bundleOf(
                            "receipt" to vehicle,
                            "refreshList" to refreshList
                        )
                    )
            }
        }
    }
}

private class VehicleDiffCallBack : DiffUtil.ItemCallback<Receipt>() {
    override fun areItemsTheSame(oldItem: Receipt, newItem: Receipt): Boolean =
        (oldItem.vehicle.plate == newItem.vehicle.plate)

    override fun areContentsTheSame(oldItem: Receipt, newItem: Receipt): Boolean =
        (oldItem.vehicle.plate == newItem.vehicle.plate)
}