package com.example.mvvmavengers.features.avengerslist.data.repository.impl

import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.features.avengerslist.data.repository.ListAvengersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * ListAvengersRepositoryImpl
 *
 * Repository class that gets avengers from injected policy
 */
@ExperimentalCoroutinesApi
class ListAvengersRepositoryImpl(private val listAvengerRepositoryPolicy: ListAvengerRepositoryPolicy) :
    ListAvengersRepository {

    override suspend fun avengersList(): ResultAvenger<AvengersModel> = listAvengerRepositoryPolicy.getAvengersList()
}
