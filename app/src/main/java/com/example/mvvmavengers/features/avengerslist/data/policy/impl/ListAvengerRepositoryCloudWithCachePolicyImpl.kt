package com.example.mvvmavengers.features.avengerslist.data.policy.impl

import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.ListAvengerCloudDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.ListAvengerDiskDataSource
import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.utils.ConnectivityHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

/**
 * ListAvengerRepositoryCloudWithCachePolicyImpl Class
 *
 *
 * <p>Cloud Policy Class with Cache</p>
 */
@ExperimentalCoroutinesApi
class ListAvengerRepositoryCloudWithCachePolicyImpl(
    private val listAvengerCloudDataSource: ListAvengerCloudDataSource,
    private val listAvengerDiskDataSource: ListAvengerDiskDataSource
) :
    ListAvengerRepositoryPolicy {

    /**
     * Get Avengers List from cloud if it possible, otherwise from disk
     *
     * @return AvengersModel avengers
     */
    override suspend fun getAvengersList(): ResultAvenger<AvengersModel> =
        if (ConnectivityHelper.isOnline) {
            when (val avengerResult = listAvengerCloudDataSource.getAvengersList()) {
                        is ResultAvenger.Success -> {
                            avengerResult.data.let {
                                if (it.data?.results.isNullOrEmpty().not()) {
                                    listAvengerDiskDataSource.setAvengers(it)
                                }
                            }

                            avengerResult
                        }
                        else -> {
                            avengerResult
                        }
                    }
        } else {
            Timber.d("Not connection")
            listAvengerDiskDataSource.getAvengersList()
        }
}
