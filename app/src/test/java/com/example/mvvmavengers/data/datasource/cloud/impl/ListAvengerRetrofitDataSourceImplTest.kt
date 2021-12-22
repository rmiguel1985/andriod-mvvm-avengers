package com.example.mvvmavengers.data.datasource.cloud.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.base.usecase.data
import com.example.mvvmavengers.base.usecase.errorMessage
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl.ListAvengerRetrofitDataSourceImpl
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream

@ExperimentalCoroutinesApi
class ListAvengerRetrofitDataSourceImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var listAvengerRetrofitDataSourceImpl: ListAvengerRetrofitDataSourceImpl
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        gson = Gson()
        listAvengerRetrofitDataSourceImpl =
                ListAvengerRetrofitDataSourceImpl(createRetrofitInstance(gson))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun createRetrofitInstance(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mockWebServer.url("/"))
                .client(OkHttpClient.Builder().build())
                .build()
    }

    @Test
    @Throws(Exception::class)
    fun getAvengersList_with_cloud_on_successful_response_returns_and_saves_expected_json() = runBlocking {

        //Given
        mockWebServer.enqueue(
            MockResponse()
                .setBody(readAsset("json/avengers_list.json"))
        )

        //When
        val avengersList = listAvengerRetrofitDataSourceImpl.getAvengersList()

        //Then
        assertNotNull(avengersList)
        assertTrue(avengersList is ResultAvenger.Success)
        assertEquals(2, avengersList.data?.data?.results?.size)
        assertEquals("3-D Man",avengersList.data?.data?.results?.get(0)?.name)
    }


    @Test
    fun getAvengersList_with_cloud_on_successful_response_with_malformed_json() = runBlocking {
        //Given
        mockWebServer.enqueue(
            MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(readAsset("json/malformed_avengers_list.json"))
        )

        //When
        val avengersList = listAvengerRetrofitDataSourceImpl.getAvengersList()

        //Then
        assertTrue(avengersList is ResultAvenger.Error)
        assertEquals(avengersList.errorMessage, "not found")
    }

    @Test
    fun getAvengersList_with_cloud_on_unsuccessful_response() = runBlocking {
        //Given
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        //When
        val avengersList =listAvengerRetrofitDataSourceImpl.getAvengersList()

        //Then
        assertTrue(avengersList is ResultAvenger.Error)
        assertEquals(avengersList.errorMessage, "not found")
    }

    /**
     * Read file from assets
     *
     * @param assetName
     * @return string from file
     */
    @Throws(IOException::class)
    private fun readAsset(assetName: String): String {
        val `is`: InputStream = ClassLoader.getSystemResourceAsStream(assetName)

        val size = `is`.available()

        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()

        return String(buffer)
    }
}