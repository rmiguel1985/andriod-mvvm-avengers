package com.example.mvvmavengers.features.avengerslist.koin

import com.example.mvvmavengers.features.avengerslist.domain.LoadAvengersListUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val ListAvengerUseCaseModule = module {
    single { LoadAvengersListUseCaseImpl(listAvengerRepository = get()) }
}
