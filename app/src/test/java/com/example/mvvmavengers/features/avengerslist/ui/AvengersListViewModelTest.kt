package com.example.mvvmavengers.features.avengerslist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.data.repository.impl.ListAvengersRepositoryImpl
import com.example.mvvmavengers.features.avengerslist.domain.LoadAvengersListUseCaseImpl
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.features.avengerslist.domain.entities.Data
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AvengersListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val stateObserver: Observer<ResultAvenger<AvengersModel>> = mockk(relaxed = true)
    private val loadAvengersRepository = mockk<ListAvengersRepositoryImpl>(relaxed = true)

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private lateinit var avengersListViewModel: AvengersListViewModel

    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        avengersListViewModel = AvengersListViewModel(LoadAvengersListUseCaseImpl(loadAvengersRepository))
        avengersListViewModel._state.observe(lifeCycleTestOwner, stateObserver)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @Test
    fun getAvengersOnSuccessResponse() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            // Given
            lifeCycleTestOwner.onResume()

            val avengersModel = AvengersModel()
            val data = Data()
            val result = com.example.mvvmavengers.features.avengerslist.domain.entities.Result()
            val avengersList = ArrayList<com.example.mvvmavengers.features.avengerslist.domain.entities.Result>()

            result.name = "3-D Man"
            result.description = "some description"
            avengersList.add(result)

            data.results = avengersList
            avengersModel.data = data

            coEvery { loadAvengersRepository.avengersList() } returns ResultAvenger.Success(avengersModel)

            // When
            avengersListViewModel.getAvengers()

            // Then
            coVerify {stateObserver.onChanged(ResultAvenger.Success(avengersModel)) }
        }
    }

    @Test
    fun getAvengersOnErrorResponse() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            // Given
            val avengerException = ResultAvenger.Error(Exception("not found"))
            lifeCycleTestOwner.onResume()
            coEvery { loadAvengersRepository.avengersList() } returns avengerException

            // When
            avengersListViewModel.getAvengers()

            // Then
            coVerify {stateObserver.onChanged(avengerException) }
        }
    }
}