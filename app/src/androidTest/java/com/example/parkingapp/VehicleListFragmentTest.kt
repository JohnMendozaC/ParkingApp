package com.example.parkingapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleListFragmentTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

//    @Before
//    fun jumpToPostFragment() {
//        activityTestRule.activity.apply {
//            runOnUiThread {
//                val bundle = Bundle().apply { putParcelable("user", testUser) }
//                findNavController(R.id.nav_host).navigate(
//                    R.id.action_userListFragment_to_userPostsFragment,
//                    bundle
//                )
//            }
//        }
//    }

    @Ignore("Press see posts")
    @Test
    fun clickSearchUser() {
        //Arrange
        Espresso.onView(withId(R.id.fab_add_vehicle)).perform(click())
        //Act
        //Assert
        Espresso.onView(withId(R.id.view_add_vehicle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}