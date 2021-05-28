package com.example.mvvmavengers

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl.ListAvengerRetrofitDataSourceImpl
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.ListAvengerDiskDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.ListAvengerRoomDataSourceImpl
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.schema.AppDatabase
import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.data.policy.impl.ListAvengerRepositoryCloudWithCachePolicyImpl
import com.example.mvvmavengers.utils.ConnectivityHelper
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertFalse
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ListAvengerRoomDataSourceImplInstrumentedTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRuleAndroid()

    private var context: Context? = null
    private lateinit var appDatabase: AppDatabase
    private lateinit var roomDiskDataSource: ListAvengerDiskDataSource
    private lateinit var cloudWithCachePolicyImpl: ListAvengerRepositoryPolicy
    private lateinit var mockWebServer: MockWebServer
    private lateinit var listAvengerRetrofitDataSourceImpl: ListAvengerRetrofitDataSourceImpl
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = Gson()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer = MockWebServer()
        context = InstrumentationRegistry.getInstrumentation().context
        appDatabase = Room.inMemoryDatabaseBuilder(context!!,
                AppDatabase::class.java).build()
        roomDiskDataSource = ListAvengerRoomDataSourceImpl(appDatabase.listAvengerDao())
        mockkObject(ConnectivityHelper)

        listAvengerRetrofitDataSourceImpl =
                ListAvengerRetrofitDataSourceImpl(createRetrofitInstance(gson))
        cloudWithCachePolicyImpl =
                ListAvengerRepositoryCloudWithCachePolicyImpl(listAvengerRetrofitDataSourceImpl,
                        roomDiskDataSource )
    }

    private fun createRetrofitInstance(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mockWebServer.url("/"))
                .client(OkHttpClient.Builder().build())
                .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        appDatabase.close()
    }

    @Test
    fun getAvengersList_on_empty_database_returns_null() = runBlocking {
        //Given
        every { ConnectivityHelper.isOnline } returns (false)

        //When
        cloudWithCachePolicyImpl.getAvengersList()
        val avengersList = appDatabase.listAvengerDao().getAll()

        //Then
        assert(avengersList.isEmpty())
    }

    @Test
    fun getAvengersList_with_saved_cached_data_returns_expected_value() = runBlocking {
        //Given

            every { ConnectivityHelper.isOnline} returns (true)
            mockWebServer.enqueue(
                    MockResponse()
                            .setBody(readAsset("json/avengers_list.json")))


            //When
            cloudWithCachePolicyImpl.getAvengersList()

            //Then
            assertFalse(appDatabase.listAvengerDao().getAll().isEmpty())


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
