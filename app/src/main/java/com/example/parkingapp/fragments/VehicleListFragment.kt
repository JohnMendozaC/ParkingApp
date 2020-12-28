package com.example.parkingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.domain.enum.Status
import com.example.parkingapp.R
import com.example.parkingapp.adapters.VehicleAdapter
import com.example.parkingapp.databinding.FragmentVehicleListBinding
import com.example.parkingapp.viewmodels.ReceiptViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleListFragment : Fragment(), VehicleListRefresh {

    private val viewModel: ReceiptViewModel by viewModels()
    private lateinit var binding: FragmentVehicleListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vehicleList.adapter = VehicleAdapter((this as VehicleListRefresh))
        subscribeUi()
        clicks()
    }

    private fun subscribeUi() {
        viewModel.getVehicles().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.loaderVehicle.isLoading = false
                    binding.listReceipt = result.data
                }
                Status.LOADING -> {
                    binding.loaderVehicle.isLoading = true
                }
                Status.ERROR -> {
                    binding.loaderVehicle.isLoading = false
                }
            }
        })
    }

    private fun clicks() {
        binding.fabAddVehicle.setOnClickListener {
            it.findNavController()
                .navigate(
                    R.id.action_vehicleListFragment_to_addVehicleDialogFragment, bundleOf(
                        "refreshList" to (this as VehicleListRefresh)
                    )
                )
        }
    }

    override fun refreshList() {
        subscribeUi()
    }
}
