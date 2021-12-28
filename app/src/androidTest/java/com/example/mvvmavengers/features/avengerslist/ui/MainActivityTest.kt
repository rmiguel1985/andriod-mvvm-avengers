package com.example.mvvmavengers.features.avengerslist.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.example.mvvmavengers.R
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerdetail.AvengerDetailActivity
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.features.avengerslist.domain.entities.Data
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@LargeTest
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var avengersListViewModel: AvengersListViewModel
    private lateinit var module: Module
    private lateinit var state: MutableLiveData<ResultAvenger<AvengersModel>>

    @Before
    fun before() {
        avengersListViewModel = mockk(relaxed = true)
        state = MutableLiveData()
        every { avengersListViewModel.uiState } returns state

        module = module(createdAtStart = true) {
            single { avengersListViewModel }
        }

        loadKoinModules(module)
        val intent = Intent(ApplicationProvider.getApplicationContext<Context>(), MainActivity::class.java)
        scenario = launchActivity(intent)
    }

    @After
    fun after() {
        scenario.close()
        unloadKoinModules(module)
    }

    @Test
    fun with_not_empty_list_of_avenger_the_recycler_view_has_element() {
        // Given
        val avengersModel = AvengersModel()
        val data = Data()
        val result = com.example.mvvmavengers.features.avengerslist.domain.entities.Result()
        val avengersList = ArrayList<com.example.mvvmavengers.features.avengerslist.domain.entities.Result>()

        result.name = "3-D Man"
        result.description = "some description"
        avengersList.add(result)

        data.results = avengersList
        avengersModel.data = data

        // When
        state.postValue(ResultAvenger.Success(avengersModel))

        // Then
        assertListNotEmpty(R.id.avenger_list_recyclerview)
        assertRecyclerViewItemCount(R.id.avenger_list_recyclerview, 1)
        assertDisplayedAtPosition(R.id.avenger_list_recyclerview, 0, "3-D Man")
    }

    @Test
    fun with_an_exception_a_snack_bar_error_is_shown_with_expected_message() {
        // Given
        val avengersModel = AvengersModel()
        val data = Data()
        val result = com.example.mvvmavengers.features.avengerslist.domain.entities.Result()
        val avengersList = ArrayList<com.example.mvvmavengers.features.avengerslist.domain.entities.Result>()
        val stringError = "No avengers found"
        val exceptionMessage =
            """
                Error
                java.lang.Exception: $stringError
            """.trimIndent()

        result.name = "3-D Man"
        result.description = "some description"
        avengersList.add(result)

        data.results = avengersList
        avengersModel.data = data

        // When
        state.postValue(ResultAvenger.Error(Exception(stringError)))

        // Then
        assertContains(exceptionMessage)
    }

    @Test
    fun when_click_on_element_of_recycler_view_there_is_an_intent_to_detail_screen() {
        // Given
        val avengersModel = AvengersModel()
        val data = Data()
        val result = com.example.mvvmavengers.features.avengerslist.domain.entities.Result()
        val avengersList = ArrayList<com.example.mvvmavengers.features.avengerslist.domain.entities.Result>()

        result.name = "3-D Man"
        result.description = "some description"
        avengersList.add(result)

        data.results = avengersList
        avengersModel.data = data

        // When
        state.postValue(ResultAvenger.Success(avengersModel))

        // Then
        Intents.init()
        clickListItem(R.id.avenger_list_recyclerview, 0)
        Intents.intended(hasComponent(AvengerDetailActivity::class.java.name))
        Intents.release()
    }
}