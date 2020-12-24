package com.example.parkingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.domain.entity.Motorcycle
import com.example.domain.enum.Status
import com.example.parkingapp.viewmodels.ReceiptViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ReceiptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.enterVehicle(
            System.currentTimeMillis(),
            Motorcycle("AJO779", 150)
        ).observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    tv_msg.text = result.message
                }
                Status.SUCCESS -> {
                    tv_msg.text = result.message
                }
                Status.ERROR -> {
                    tv_msg.text = result.message
                }
            }
        })

    }
}