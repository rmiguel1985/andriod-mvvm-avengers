package com.example.mvvmavengers.features.avengerslist.koin

import com.example.mvvmavengers.features.avengerslist.ui.AvengersListViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
val ListAvengerViewModelModule = module {
    viewModel { AvengersListViewModel(loadAvengersListUseCaseImpl = get()) }
}
