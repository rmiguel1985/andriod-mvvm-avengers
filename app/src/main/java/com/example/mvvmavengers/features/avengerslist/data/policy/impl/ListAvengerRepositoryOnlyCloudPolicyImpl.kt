package com.example.mvvmavengers.features.avengerslist.data.policy.impl

import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.ListAvengerCloudDataSource
import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.utils.ConnectivityHelper
import java.lang.Exception

/**
 * ListAvengerRepositoryOnlyCloudPolicyImpl Class
 *
 * <p>Cloud Policy Class</p>
 */
class ListAvengerRepositoryOnlyCloudPolicyImpl
(private val listAvengerCloudDataSource: ListAvengerCloudDataSource) : ListAvengerRepositoryPolicy {

    /**
     * Get Avengers List from cloud
     *
     * @return AvengersModel avengers
     */
    override suspend fun getAvengersList(): ResultAvenger<AvengersModel> =
        if (ConnectivityHelper.isOnline) {
            when (val avengerResult = listAvengerCloudDataSource.getAvengersList()) {
                is ResultAvenger.Success -> {
                    avengerResult
                }
                else -> {
                    avengerResult
                }
            }
        } else {
            ResultAvenger.Error(Exception("not found"))
        }
}
