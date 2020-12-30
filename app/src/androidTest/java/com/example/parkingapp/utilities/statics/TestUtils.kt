package com.example.parkingapp.utilities.statics

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.example.domain.enums.VehicleType
import com.example.infrastructure.dblocal.entitys.ReceiptEntity
import org.hamcrest.Matcher

/*1608818170116 24/12/2020 8 AM*/
val testVehicles = arrayListOf(
    ReceiptEntity("ABJ78F", 1608818170116,VehicleType.CAR.value),
    ReceiptEntity("BBJ78F", 1608818170116,VehicleType.CAR.value),
    ReceiptEntity("CBJ78F", 1608818170116,VehicleType.CAR.value)
)

val testVehicle = testVehicles[0]

fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadForAtLeast(delay)
        }

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isRoot()
        }

        override fun getDescription(): String {
            return "wait for " + delay + "milliseconds"
        }
    }
}