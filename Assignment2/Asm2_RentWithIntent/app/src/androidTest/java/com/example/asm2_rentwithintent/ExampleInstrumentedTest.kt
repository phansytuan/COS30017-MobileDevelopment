package com.example.asm2_rentwithintent

import android.view.View
import android.widget.RatingBar
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testNextButtonNavigatesThroughItems() {
        // Launch MainActivity.
        ActivityScenario.launch(MainActivity::class.java)

        // Verify the initial item is "🎹 Piano".
        onView(withId(R.id.itemName))
            .check(matches(withText("\uD83C\uDFB9 Piano")))

        // Click "Next" to navigate to the second item.
        onView(withId(R.id.nextButton)).perform(click())
        onView(withId(R.id.itemName))
            .check(matches(withText("\uD83E\uDD41 Drum")))

        // Click "Next" again to navigate to the third item.
        onView(withId(R.id.nextButton)).perform(click())
        onView(withId(R.id.itemName))
            .check(matches(withText("\uD83C\uDFB7 Saxophone")))
    }

    @Test
    fun testAccessoryDialogAddAccessory() {
        // Launch MainActivity.
        ActivityScenario.launch(MainActivity::class.java)

        // Tap on the Accessories TextView to open the accessory update dialog.
        onView(withId(R.id.itemAccessories)).perform(click())

        // In the dialog, type "NewAccessory" into the EditText.
        onView(withHint("Enter new accessory"))
            .inRoot(isDialog())
            .perform(typeText("NewAccessory"), closeSoftKeyboard())

        // Click the "Add" button in the dialog.
        onView(withText("Add"))
            .inRoot(isDialog())
            .perform(click())

        // Verify that the accessory list now contains the new accessory.
        onView(withId(R.id.itemAccessories))
            .check(matches(withText(containsString("NewAccessory"))))
    }

    @Test
    fun testRatingBarUpdates() {
        // Launch MainActivity.
        ActivityScenario.launch(MainActivity::class.java)

        // Use a custom action to set the rating bar to 3.5.
        onView(withId(R.id.ratingBar)).perform(setRating(3.5f))

        // Verify that the RatingBar's rating is now 3.5 using our custom matcher.
        onView(withId(R.id.ratingBar)).check(matches(withRating(3.5f)))
    }

    // Custom ViewAction to set the rating on a RatingBar.
    private fun setRating(rating: Float): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String = "Set RatingBar rating to $rating"
            override fun getConstraints(): Matcher<View> = isAssignableFrom(RatingBar::class.java)
            override fun perform(uiController: UiController, view: View) {
                (view as RatingBar).rating = rating
            }
        }
    }

    // Custom matcher to check the RatingBar's rating.
    private fun withRating(expectedRating: Float): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("RatingBar with rating: $expectedRating")
            }
            override fun matchesSafely(item: View?): Boolean {
                if (item !is RatingBar) return false
                return item.rating == expectedRating
            }
        }
    }
}
