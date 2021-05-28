package com.example.mvvmavengers.features.avengerslist.data.datasource.disk

import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel

interface ListAvengerDiskDataSource {
    suspend fun getAvengersList(): ResultAvenger<AvengersModel>
    suspend fun setAvengers(avengers: AvengersModel)
}
