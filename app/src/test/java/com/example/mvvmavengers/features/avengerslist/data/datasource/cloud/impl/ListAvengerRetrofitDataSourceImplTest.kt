package com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.base.usecase.data
import com.example.mvvmavengers.base.usecase.errorMessage
import com.example.mvvmavengers.base.usecase.exception
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import java.io.IOException
import java.io.InputStream

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
class ListAvengerRetrofitDataSourceImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var listAvengerRetrofitDataSourceImpl: ListAvengerRetrofitDataSourceImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        listAvengerRetrofitDataSourceImpl =
            ListAvengerRetrofitDataSourceImpl(createRetrofitInstance())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun createRetrofitInstance(): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Test
    @Throws(Exception::class)
    fun `getAvengersList with successful cloud response returns and saves expected json`() = runBlocking {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setBody(readAsset("json/avengers_list.json"))
        )

        // When
        val avengersList = listAvengerRetrofitDataSourceImpl.getAvengersList()

        // Then
        assertNotNull(avengersList)
        assertTrue(avengersList is ResultAvenger.Success)
        assertEquals(2, avengersList.data?.data?.results?.size)
        assertEquals("3-D Man", avengersList.data?.data?.results?.get(0)?.name)
    }

    @Test
    fun `getAvengersList with cloud on successful response with malformed json`() = runBlocking {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(readAsset("json/malformed_avengers_list.json"))
        )

        // When
        val avengersList = listAvengerRetrofitDataSourceImpl.getAvengersList()

        // Then
        assertTrue(avengersList is ResultAvenger.Error)
        assertTrue(avengersList.exception is IllegalArgumentException)
    }

    @Test
    fun `getAvengersList with cloud on unsuccessful response`() = runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        // When
        val avengersList = listAvengerRetrofitDataSourceImpl.getAvengersList()

        // Then
        assertTrue(avengersList is ResultAvenger.Error)
        assertEquals(avengersList.errorMessage, "not found")
    }

    @Test
    fun `getAvengersList with cloud on unsuccessful response with http exception`() = runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("code:'500'"))

        // When
        val avengersList = listAvengerRetrofitDataSourceImpl.getAvengersList()

        // Then
        assertTrue(avengersList is ResultAvenger.Error)
        assertEquals(avengersList.errorMessage, "HTTP 500 Server Error")
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