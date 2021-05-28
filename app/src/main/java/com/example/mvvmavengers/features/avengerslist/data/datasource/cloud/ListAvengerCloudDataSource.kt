package com.example.mvvmavengers.features.avengerslist.data.datasource.cloud

import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.base.usecase.ResultAvenger

interface ListAvengerCloudDataSource {
    suspend fun getAvengersList(): ResultAvenger<AvengersModel>
}
