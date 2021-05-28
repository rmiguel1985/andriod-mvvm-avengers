package com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl

import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.ListAvengerCloudDataSource
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.utils.Constants.API_END_POINT
import com.example.mvvmavengers.utils.Constants.API_HASH
import com.example.mvvmavengers.utils.Constants.API_KEY
import com.example.mvvmavengers.utils.Constants.LIMIT
import com.example.mvvmavengers.utils.Constants.TIME_STAMP
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.IllegalArgumentException

/**
 * ListAvengerRetrofitDataSourceImpl
 *
 * Cloud Data Source Class that gets avengers list with Retrofit
 */
@Suppress("UNREACHABLE_CODE")
@ExperimentalCoroutinesApi
class ListAvengerRetrofitDataSourceImpl(private val retrofitInstance: Retrofit) :
    ListAvengerCloudDataSource {

    override suspend fun getAvengersList(): ResultAvenger<AvengersModel> =
        try {
            val call = retrofitInstance
                .create(ListAvengersApi::class.java)
                .getCharacters(API_END_POINT, LIMIT, API_KEY, TIME_STAMP, API_HASH)

            ResultAvenger.Success(call)
        } catch (e: HttpException) {
            ResultAvenger.Error(e)
        } catch (e: IllegalArgumentException) {
            ResultAvenger.Error(e)
        } catch (e: Throwable) {
            ResultAvenger.Error(Exception("not found"))
        }
}
