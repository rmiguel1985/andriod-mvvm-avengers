package com.example.mvvmavengers.data.policy.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl.ListAvengerRetrofitDataSourceImpl
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.ListAvengerRoomDataSourceImpl
import com.example.mvvmavengers.features.avengerslist.data.policy.impl.ListAvengerRepositoryCloudWithCachePolicyImpl
import com.example.mvvmavengers.utils.ConnectivityHelper
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


@ExperimentalCoroutinesApi
class ListAvengerRepositoryCloudWithCachePolicyImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val listAvengerDiskDataSource = mockk<ListAvengerRoomDataSourceImpl>()

    private val listAvengerCloudDataSourceImpl = mockk<ListAvengerRetrofitDataSourceImpl>()

    private lateinit var listAvengerRepositoryCloudWithCachePolicyImpl: ListAvengerRepositoryCloudWithCachePolicyImpl

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        mockkObject(ConnectivityHelper)
        listAvengerRepositoryCloudWithCachePolicyImpl = ListAvengerRepositoryCloudWithCachePolicyImpl(
                listAvengerCloudDataSourceImpl, listAvengerDiskDataSource)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAvengersList_without_connectivity_calls_disk_data_source() = runBlockingTest {
        //Given
        coEvery { listAvengerDiskDataSource.getAvengersList() } returns ResultAvenger.Error(
            Exception("Error")
        )
        every { ConnectivityHelper.isOnline } returns (false)

        //When
        listAvengerRepositoryCloudWithCachePolicyImpl.getAvengersList()

        //Then
        coVerify { listAvengerDiskDataSource.getAvengersList() }
    }

    @Test
    @Throws(IOException::class)
    fun getAvengersList_with_connectivity_calls_cloud_data_source() = runBlockingTest {
        //Given
        coEvery { listAvengerCloudDataSourceImpl.getAvengersList() } returns ResultAvenger.Error(
            Exception("Error")
        )
        every { ConnectivityHelper.isOnline } returns (true)

        //When
        listAvengerRepositoryCloudWithCachePolicyImpl.getAvengersList()

        //Then
        coVerify { listAvengerCloudDataSourceImpl.getAvengersList() }
    }
}