package pl.daftacademy.androidlevelup.view.viewmodel

import android.view.Gravity
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.daftacademy.androidlevelup.R
import pl.daftacademy.androidlevelup.view.home.HomeActivity

class HomeActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun clickedHomeIconOpensDrawer() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START)))
        clickHomeToOpenDrawer()
        checkDrawerIsOpen()
    }

    private fun clickHomeToOpenDrawer() {
        onView(withContentDescription(activityRule.activity.findViewById<Toolbar>(R.id.toolbar).navigationContentDescription as String)).perform(click())
    }

    private fun checkDrawerIsOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.START)))
    }

    private fun checkDrawerIsNotOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START)))
    }
}