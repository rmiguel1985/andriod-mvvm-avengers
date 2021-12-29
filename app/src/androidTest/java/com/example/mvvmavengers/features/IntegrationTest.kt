package com.example.mvvmavengers.features


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.adevinta.android.barista.assertion.BaristaListAssertions
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickBack
import com.adevinta.android.barista.interaction.BaristaListInteractions
import com.adevinta.android.barista.interaction.BaristaSwipeRefreshInteractions.refresh
import com.example.mvvmavengers.R
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl.ListAvengerRetrofitDataSourceImpl
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.ListAvengerDiskDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.ListAvengerRoomDataSourceImpl
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.schema.AppDatabase
import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.data.policy.impl.ListAvengerRepositoryCloudWithCachePolicyImpl
import com.example.mvvmavengers.features.avengerslist.data.repository.impl.ListAvengersRepositoryImpl
import com.example.mvvmavengers.features.avengerslist.domain.LoadAvengersListUseCaseImpl
import com.example.mvvmavengers.features.avengerslist.ui.AvengersListViewModel
import com.example.mvvmavengers.features.avengerslist.ui.MainActivity
import com.example.mvvmavengers.utils.ConnectivityHelper
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.asExecutor
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class IntegrationTest {
    private lateinit var listAvengersRepositoryImpl: ListAvengersRepositoryImpl
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var module: Module

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var context: Context? = null
    private lateinit var appDatabase: AppDatabase
    private lateinit var roomDiskDataSource: ListAvengerDiskDataSource
    private lateinit var cloudWithCachePolicyImpl: ListAvengerRepositoryPolicy
    private lateinit var mockWebServer: MockWebServer
    private lateinit var listAvengerRetrofitDataSourceImpl: ListAvengerRetrofitDataSourceImpl
    private lateinit var gson: Gson

    @Before
    fun before() {
        context = InstrumentationRegistry.getInstrumentation().context
        initMockWebServer()
        createDataLayerDependencies()
        loadKoinModule()

        every { ConnectivityHelper.isOnline } returns (true)
        scenario = launchActivity()
    }

    @After
    fun after() {
        scenario.close()
        unloadKoinModules(module)
    }

    private fun createDataLayerDependencies() {
        gson = Gson()
        val transactionQueryExecutor = Dispatchers.IO.asExecutor()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context!!,
            AppDatabase::class.java
        ).setQueryExecutor(transactionQueryExecutor)
            .setTransactionExecutor(transactionQueryExecutor).build()
        roomDiskDataSource = ListAvengerRoomDataSourceImpl(appDatabase.listAvengerDao())
        mockkObject(ConnectivityHelper)

        listAvengerRetrofitDataSourceImpl =
            ListAvengerRetrofitDataSourceImpl(createRetrofitInstance(gson))
        cloudWithCachePolicyImpl =
            ListAvengerRepositoryCloudWithCachePolicyImpl(
                listAvengerRetrofitDataSourceImpl,
                roomDiskDataSource
            )
        listAvengersRepositoryImpl = ListAvengersRepositoryImpl(cloudWithCachePolicyImpl)
    }

    private fun loadKoinModule() {
        module = module(createdAtStart = true) {
            single { }
            single { AvengersListViewModel(LoadAvengersListUseCaseImpl(listAvengersRepositoryImpl)) }
        }

        loadKoinModules(module)
    }

    private fun initMockWebServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(
            MockResponse()
                .setBody(readAsset("json/avengers_list.json"))
        )
    }

    private fun createRetrofitInstance(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Test
    fun integrationTest() {
        val expectedAvengerName = "3-D Man"
        val expectedAvengerLastModified = "2014-04-29T14:18:17-0400"
        val expectedAvengerDescription = "Description"

        BaristaListAssertions.assertListNotEmpty(R.id.avenger_list_recyclerview)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.avenger_list_recyclerview, 2)
        BaristaListAssertions.assertDisplayedAtPosition(R.id.avenger_list_recyclerview, 0, expectedAvengerName)
        BaristaListInteractions.clickListItem(R.id.avenger_list_recyclerview, 0)

        onView(withId(R.id.detail_title))
            .check(matches(withText(expectedAvengerName)))
        onView(withId(R.id.avenger_detail))
            .check(matches(withText(expectedAvengerDescription)))
        onView(withId(R.id.detail_date))
            .check(matches(withText(expectedAvengerLastModified)))

        clickBack()
        every { ConnectivityHelper.isOnline } returns (false)
        refresh()

        BaristaListAssertions.assertListNotEmpty(R.id.avenger_list_recyclerview)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.avenger_list_recyclerview, 2)
        BaristaListAssertions.assertDisplayedAtPosition(R.id.avenger_list_recyclerview, 0, expectedAvengerName)
    }

    /**
     * Read file from assets
     *
     * @param assetName
     * @return string from file
     */
    @Throws(IOException::class)
    private fun readAsset(assetName: String): String {
        val `is` = context!!.assets.open(assetName)

        val size = `is`.available()

        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()

        return String(buffer)
    }
}
