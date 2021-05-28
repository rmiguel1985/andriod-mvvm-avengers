package com.example.mvvmavengers.features.avengerslist.koin

import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.ListAvengerDiskDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.ListAvengerRoomDataSourceImpl
import org.koin.dsl.module

val ListAvengersDiskDatasourcesModule = module {
    single<ListAvengerDiskDataSource> { ListAvengerRoomDataSourceImpl(get()) }
}
