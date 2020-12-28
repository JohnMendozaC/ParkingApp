package com.example.parkingapp.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.enum.Status
import com.example.domain.util.ConvertDate.getLongDate
import com.example.domain.valueobject.Vehicle
import com.example.parkingapp.R
import com.example.parkingapp.databinding.DialogFragmentAddVehicleBinding
import com.example.parkingapp.viewmodels.ReceiptViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddVehicleDialogFragment : DialogFragment() {

    private val viewModel: ReceiptViewModel by viewModels()
    private lateinit var binding: DialogFragmentAddVehicleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentAddVehicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        requireDialog().window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clicksEntry()
        clicksProcessing()
    }

    @SuppressLint("SetTextI18n")
    private fun clicksEntry() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                dismiss()
            }
            etDate.setOnClickListener {
                val c = Calendar.getInstance()
                DatePickerDialog(
                    requireContext(),
                    OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        etDate.text = "Fecha: ${dayOfMonth}/${(monthOfYear + 1)}/${year}"
                        etDate.tag = "${dayOfMonth}/${(monthOfYear + 1)}/${year}"
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            etTime.setOnClickListener {
                val c = Calendar.getInstance()
                TimePickerDialog(
                    requireContext(),
                    OnTimeSetListener { _, hourOfDay, minute ->
                        etTime.text = "Hora: $hourOfDay:$minute"
                        etTime.tag = "$hourOfDay:$minute"
                    },
                    c[Calendar.HOUR_OF_DAY],
                    c[Calendar.MINUTE],
                    false
                ).show()
            }
        }
    }

    private fun clicksProcessing() {
        with(binding) {
            mbAddVehicle.setOnClickListener {
                val resultValid = validateData()
                if (resultValid.first) {
                    saveData(resultValid.second, getReceipt())
                } else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(resources.getString(R.string.error_data))
                        .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                        }
                        .show()
                }
            }
        }
    }

    private fun getReceipt(): Vehicle {
        return if (binding.rbCar.isChecked) {
            Car(binding.etPlate.text.toString())
        } else {
            Motorcycle(
                binding.etPlate.text.toString(),
                binding.etCylinderCapacity.text.toString().toInt()
            )
        }
    }

    private fun validateData(): Pair<Boolean, Long> {
        val isValid: Boolean
        val date: Long
        with(binding) {
            date = "${etDate.tag as String?} ${etTime.tag as String?}".getLongDate()
            isValid = when {
                (date <= 0) -> false
                (etPlate.text.toString().isEmpty()) -> false
                (binding.rbMoto.isChecked && etCylinderCapacity.text.toString().isEmpty()) -> false
                else -> true
            }
        }
        return Pair(isValid, date)
    }

    private fun saveData(date: Long, vehicle: Vehicle) {
        viewModel.enterVehicle(date, vehicle)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        binding.loaderAddVehicle.isLoading = false
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(resources.getString(R.string.info))
                            .setMessage(result.message)
                            .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                                (arguments?.get("refreshList") as VehicleListRefresh?)?.refreshList()
                                findNavController(this@AddVehicleDialogFragment)
                                    .navigate(
                                        R.id.action_addVehicleDialogFragment_pop,
                                        null
                                    )
                            }
                            .show()
                    }
                    Status.LOADING -> {
                        binding.loaderAddVehicle.isLoading = true
                    }
                    Status.ERROR -> {
                        binding.loaderAddVehicle.isLoading = false
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(resources.getString(R.string.error))
                            .setMessage(result.message)
                            .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                            }
                            .show()
                    }
                }
            })
    }

}
