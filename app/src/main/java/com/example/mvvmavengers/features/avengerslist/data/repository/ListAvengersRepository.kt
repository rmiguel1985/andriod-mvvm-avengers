package com.example.mvvmavengers.features.avengerslist.data.repository

import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel

interface ListAvengersRepository {
    suspend fun avengersList(): ResultAvenger<AvengersModel>
}
