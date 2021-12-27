package com.example.mvvmavengers.features.avengerslist.koin

import com.example.mvvmavengers.features.avengerslist.data.repository.ListAvengersRepository
import com.example.mvvmavengers.features.avengerslist.data.repository.impl.ListAvengersRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val ListAvengerRepositoryModule = module {
    single<ListAvengersRepository> { ListAvengersRepositoryImpl(listAvengerRepositoryPolicy = get()) }
}
