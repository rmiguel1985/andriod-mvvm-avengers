package com.example.mvvmavengers.base.koin

import androidx.room.Room
import com.example.mvvmavengers.BuildConfig
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.schema.AppDatabase
import com.example.mvvmavengers.utils.Constants.DATABASE_NAME
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private var baseUrl: String = BuildConfig.API_BASE_URL + ":" + BuildConfig.API_PORT + BuildConfig.API_URL
private const val TIMEOUT_CONNECT_MS: Long = 8000
private const val TIMEOUT_READ_MS: Long = 8000

@OptIn(ExperimentalSerializationApi::class)
val MainModule = module() {
    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_READ_MS, TimeUnit.MILLISECONDS)

        if (BuildConfig.DEBUG) {
            // OKHHTP LOGGER
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }

        client.build()
    }

    single {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    single {
        val transactionQueryExecutor = Dispatchers.IO.asExecutor()
        Room
            .databaseBuilder(
                androidApplication(),
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .setQueryExecutor(transactionQueryExecutor)
            .setTransactionExecutor(transactionQueryExecutor)
            .fallbackToDestructiveMigration()
            .build().listAvengerDao()
    }
}
