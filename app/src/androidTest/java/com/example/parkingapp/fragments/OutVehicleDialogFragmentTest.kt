package com.example.parkingapp.fragments

import androidx.test.espresso.matcher.ViewMatchers.*

import android.widget.DatePicker
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.parkingapp.MainActivity
import com.example.parkingapp.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class OutVehicleDialogFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Before
    fun addItemListVehicle() {
        //Arrange
        onView(withId(R.id.fab_add_vehicle)).perform(click())

        //Act
        onView(withId(R.id.et_plate))
            .perform(typeText("NJO65F"), closeSoftKeyboard())
        onView(withId(R.id.et_date_add)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2020, 12, 29))
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.et_time_add)).perform(click())
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
            .perform(PickerActions.setTime(8, 10))
        onView(withId(android.R.id.button1)).perform(click())

        //Assert
        onView(withId(R.id.mb_add_vehicle)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
        onView(isRoot()).perform(pressBack())
    }


    @Test
    fun outVehicle() {
        //Arrange
        onView(withId(R.id.vehicle_list))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.vehicle_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )

        //Act
        onView(withId(R.id.et_date_out)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2020, 12, 29))
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.et_time_out)).perform(click())
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
            .perform(PickerActions.setTime(11, 10))
        onView(withId(android.R.id.button1)).perform(click())

        //Assert
        onView(withId(R.id.mb_out_vehicle)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
    }
}
