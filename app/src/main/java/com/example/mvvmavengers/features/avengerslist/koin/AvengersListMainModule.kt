package com.example.mvvmavengers.features.avengerslist.koin

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
val avengersListMainModule = listOf(
        ListAvengerPoliciesModule,
        ListAvengerViewModelModule,
        ListAvengerRepositoryModule,
        ListAvengersCloudDataSourceModule,
        ListAvengerUseCaseModule,
        ListAvengersDiskDatasourcesModule
)
