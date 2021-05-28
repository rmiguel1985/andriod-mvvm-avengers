package com.example.mvvmavengers.features.avengerslist.data.policy

import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.base.usecase.ResultAvenger

interface ListAvengerRepositoryPolicy {
    suspend fun getAvengersList(): ResultAvenger<AvengersModel>
}
