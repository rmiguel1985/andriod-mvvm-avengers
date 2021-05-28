package com.example.mvvmavengers.features.avengerslist.data.policy.impl

import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.base.usecase.data
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.ListAvengerCloudDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.ListAvengerDiskDataSource
import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.utils.ConnectivityHelper
import java.lang.Exception

/**
 * ListAvengerRepositoryCacheWithCloudPolicyImpl Class
 *
 *
 * <p>Cache Policy Class with Cloud</p>
 */
class ListAvengerRepositoryCacheWithCloudPolicyImpl(
    private val listAvengerCloudDataSource: ListAvengerCloudDataSource,
    private val listAvengerDiskDataSource: ListAvengerDiskDataSource
) : ListAvengerRepositoryPolicy {

    /**
     * Get Avengers List from disk if it possible, otherwise from cloud
     *
     * @return AvengersModel avengers
     */
    override suspend fun getAvengersList(): ResultAvenger<AvengersModel> =
        when (val avengerResult: ResultAvenger<AvengersModel> = listAvengerDiskDataSource.getAvengersList()) {
            is ResultAvenger.Error -> {
                if (ConnectivityHelper.isOnline) {
                    listAvengerCloudDataSource.getAvengersList().data.let {
                        it?.let {
                            if (it.data?.results.isNullOrEmpty().not()) {
                                listAvengerDiskDataSource.setAvengers(it)
                            }
                        }
                    }
                    avengerResult
                } else {
                    ResultAvenger.Error(Exception("not found"))
                }
            }
            else -> {
                ResultAvenger.Error(Exception("not found"))
            }
        }
}
