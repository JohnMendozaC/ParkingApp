package com.example.parkingapp.fragments

import android.widget.DatePicker
import android.widget.TimePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.parkingapp.MainActivity
import com.example.parkingapp.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AddVehicleDialogFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test
    fun addVehicle() {
        //Arrange
        onView(withId(R.id.fab_add_vehicle)).perform(click())

        //Act
        onView(withId(R.id.et_plate))
            .perform(typeText("MJO65F"), closeSoftKeyboard())
        onView(withId(R.id.et_cylinder_capacity))
            .perform(typeText("150"), closeSoftKeyboard())
        onView(withId(R.id.et_date_add)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2020, 12, 29))
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.et_time_add)).perform(click())
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
            .perform(PickerActions.setTime(8, 10))
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.mb_add_vehicle)).perform(click())

        //Assert
        onView(withId(android.R.id.button1)).perform(click())
    }

}
