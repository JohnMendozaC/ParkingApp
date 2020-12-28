package com.example.parkingapp.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.domain.aggregate.Receipt
import com.example.domain.enum.Status
import com.example.domain.util.ConvertDate.getLongDate
import com.example.parkingapp.R
import com.example.parkingapp.databinding.DialogFragmentOutVehicleBinding
import com.example.parkingapp.viewmodels.ReceiptViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OutVehicleDialogFragment : DialogFragment() {

    private val viewModel: ReceiptViewModel by viewModels()
    private lateinit var binding: DialogFragmentOutVehicleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentOutVehicleBinding.inflate(inflater, container, false)
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
        binding.receipt = arguments?.get("receipt") as Receipt?
        clicksEntry()
        clicksProcessing()
    }

    private fun clicksEntry() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                dismiss()
            }
            etDate.setOnClickListener {
                val c = Calendar.getInstance()
                DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        etDate.text = resources.getString(
                            R.string.date_to,
                            "${dayOfMonth}/${(monthOfYear + 1)}/${year}"
                        )
                        etDate.tag = "${dayOfMonth}/${(monthOfYear + 1)}/${year}"
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            etTime.setOnClickListener {
                val c = Calendar.getInstance()
                TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        etTime.text = resources.getString(R.string.time_to, "$hourOfDay:$minute")
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
                val resultValid = validData()
                val receiptOut = arguments?.get("receipt") as Receipt
                if (resultValid.first) {
                    saveData(resultValid.second, receiptOut)
                } else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(resources.getString(R.string.error_date))
                        .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                        }
                        .show()
                }
            }
        }
    }

    private fun validData(): Pair<Boolean, Long> {
        val isValid: Boolean
        val date: Long
        with(binding) {
            date = "${etDate.tag as String?} ${etTime.tag as String?}".getLongDate()
            isValid = when {
                (date <= 0) -> false
                (date <= receipt?.entryDate ?: 0) -> false
                else -> true
            }
        }
        return Pair(isValid, date)
    }

    private fun saveData(date: Long, receiptOut: Receipt) {
        viewModel.takeOutVehicle(date, receiptOut)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        binding.loaderOutVehicle.isLoading = false
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(resources.getString(R.string.info))
                            .setMessage(
                                resources.getString(
                                    R.string.amount_to_pay,
                                    result.data.toString()
                                )
                            )
                            .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                                (arguments?.get("refreshList") as VehicleListRefresh?)?.refreshList()
                                NavHostFragment.findNavController(this@OutVehicleDialogFragment)
                                    .navigate(R.id.action_outVehicleDialogFragment_pop, null)
                            }
                            .show()
                    }
                    Status.LOADING -> {
                        binding.loaderOutVehicle.isLoading = true
                    }
                    Status.ERROR -> {
                        binding.loaderOutVehicle.isLoading = false
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