package com.example.mvvmavengers.features.avengerdetail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.mvvmavengers.R
import com.example.mvvmavengers.features.avengerslist.domain.entities.Avenger
import com.example.mvvmavengers.utils.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class AvengerDetailActivityTest {
    @get:Rule
    val rule = lazyActivityScenarioRule<AvengerDetailActivity>(launchActivity = false)

    @Test
    fun `on intent with avenger model expected info is shown`() {
        //Given
        val avengerName = "3-D Man"
        val avengerDescription = "Avenger"
        val avengerDataLasModified = "2014-04-29T14:18:17-0400"
        val intent = Intent(ApplicationProvider.getApplicationContext(), AvengerDetailActivity::class.java)
        intent.putExtra(
            Constants.AVENGER_KEY, Avenger(
                (avengerName),
                (avengerDataLasModified),
                ("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"),
                (avengerDescription)
            )
        )

        // When
        rule.launch(intent)

        // Then
        onView(withId(R.id.detail_title))
            .check(matches(withText(avengerName)))
        onView(withId(R.id.avenger_detail))
            .check(matches(withText(avengerDescription)))
        onView(withId(R.id.detail_date))
            .check(matches(withText(avengerDataLasModified)))
    }
}