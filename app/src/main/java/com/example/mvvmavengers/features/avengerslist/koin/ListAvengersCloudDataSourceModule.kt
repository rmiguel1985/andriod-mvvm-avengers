package com.example.mvvmavengers.features.avengerslist.koin

import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.ListAvengerCloudDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.cloud.impl.ListAvengerRetrofitDataSourceImpl
import org.koin.dsl.module

@kotlinx.coroutines.ExperimentalCoroutinesApi
val ListAvengersCloudDataSourceModule = module {
    single<ListAvengerCloudDataSource> { ListAvengerRetrofitDataSourceImpl(retrofitInstance = get()) }
}
