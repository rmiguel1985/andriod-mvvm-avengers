package com.example.mvvmavengers.features.avengerslist.domain

import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.base.usecase.SuspendUseCase
import com.example.mvvmavengers.features.avengerslist.data.repository.ListAvengersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * LoadAvengersListUseCaseImpl
 *
 * Use Case class that gets avengers list from repository
 */
@ExperimentalCoroutinesApi
class LoadAvengersListUseCaseImpl(private val listAvengerRepository: ListAvengersRepository) :
    SuspendUseCase<AvengersModel, Any>() {

    override suspend fun execute(params: Any): ResultAvenger<AvengersModel> =
        listAvengerRepository.avengersList()
}
