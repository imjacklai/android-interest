package tw.ctl.interest

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculationScreenTest {

    @Rule @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testCalculation() {
        onView(withId(R.id.principalField)).perform(typeText("1000"), closeSoftKeyboard())
        onView(withId(R.id.interestField)).perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.periodField)).perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.investField)).perform(typeText("1000"), closeSoftKeyboard())
        onView(withId(R.id.calculateButton)).perform(click())

        onView(withId(R.id.simpleInterestResult)).check(matches(withText("2,000.00")))
        onView(withId(R.id.compoundInterestResult)).check(matches(withText("2,593.74")))
        onView(withId(R.id.investInterestResult)).check(matches(withText("18,531.17")))
    }

    @Test
    fun testCalculationWithFieldsEmpty() {
        onView(withId(R.id.calculateButton)).perform(click())

        onView(withId(R.id.principalField)).check(matches(hasErrorText("請輸入")))
        onView(withId(R.id.interestField)).check(matches(hasErrorText("請輸入")))
        onView(withId(R.id.periodField)).check(matches(hasErrorText("請輸入")))
    }

    @Test
    fun testClear() {
        onView(withId(R.id.principalField)).perform(typeText("1000"), closeSoftKeyboard())
        onView(withId(R.id.interestField)).perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.periodField)).perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.investField)).perform(typeText("1000"), closeSoftKeyboard())
        onView(withId(R.id.clearButton)).perform(click())

        onView(withId(R.id.principalField)).check(matches(withText("")))
        onView(withId(R.id.interestField)).check(matches(withText("")))
        onView(withId(R.id.periodField)).check(matches(withText("")))
        onView(withId(R.id.investField)).check(matches(withText("")))
    }

}
