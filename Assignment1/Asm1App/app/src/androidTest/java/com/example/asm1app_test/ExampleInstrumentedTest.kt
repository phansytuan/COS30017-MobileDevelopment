package com.example.asm1app_test

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    /** test that on launch the score is initially "0". */
    @Test
    fun testInitialState() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.scoreTextView)).check(matches(withText("0")))
        }
    }

    /** test that pressing the Climb button once increments the score by 1 (blue zone). */
    @Test
    fun testClimbIncrementsScore() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("1")))
        }
    }

    /** test multiple climbs: first three climbs (blue zone) add 1 point each,
     *  and the fourth climb (green zone) adds 2 points.
     */
    @Test
    fun testMultipleClimbIncrements() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // perform three climbs: Score should become 3 (1 point per hold in blue zone)
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("3")))

            // fourth climb: Now in green zone; score increases by 2 points, expected score = 5
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("5")))
        }
    }

    /** test that pressing the Fall button before any climb does nothing. */
    @Test
    fun testFallBeforeAnyClimb() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // press fall button without any climb
            onView(withId(R.id.fallButton)).perform(click())
            // score should remain at 0
            onView(withId(R.id.scoreTextView)).check(matches(withText("0")))
        }
    }

    /** test that falling after a climb subtracts 3 points (but not below 0)
     *  and prevents further climbs.
     */
    @Test
    fun testFallAfterClimbPreventsFurtherClimb() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // climb once: Score becomes 1.
            onView(withId(R.id.climbButton)).perform(click())
            // fall: Score becomes max(1 - 3, 0) = 0, and no further climbs are allowed.
            onView(withId(R.id.fallButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("0")))
            // try to climb after falling – the score should remain unchanged.
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("0")))
        }
    }

    /** test that the Reset button clears the game state, allowing climbing again. */
    @Test
    fun testResetFunctionality() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // simulate a couple of climbs.
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("2")))

            // press reset and check that the score resets to "0".
            onView(withId(R.id.resetButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("0")))

            // verify that climbing works again after reset.
            onView(withId(R.id.climbButton)).perform(click())
            onView(withId(R.id.scoreTextView)).check(matches(withText("1")))
        }
    }
}
