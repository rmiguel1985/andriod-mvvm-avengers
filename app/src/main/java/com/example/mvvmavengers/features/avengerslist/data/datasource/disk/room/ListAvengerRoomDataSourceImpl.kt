package com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room

import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.ListAvengerDiskDataSource
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.dao.ListAvengersDao
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.features.avengerslist.domain.entities.Data
import com.example.mvvmavengers.features.avengerslist.domain.entities.Result
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.mapper.AvengerMapper.transformEntityToModel
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.mapper.AvengerMapper.transformModelToEntity
import java.lang.Exception

/**
 * ListAvengerRoomDataSourceImpl
 *
 * Disk Data Source Class that gets avengers list Room
 */
class ListAvengerRoomDataSourceImpl(private val roomDao: ListAvengersDao) : ListAvengerDiskDataSource {

    override suspend fun getAvengersList(): ResultAvenger<AvengersModel> {
        val avengersModel = AvengersModel()
        avengersModel.data = Data()

        val avengersList: List<Result> = roomDao.getAll().transformEntityToModel()

        avengersModel.data?.results = avengersList

        return if (avengersModel.data?.results.isNullOrEmpty().not()) {
            ResultAvenger.Success(avengersModel)
        } else {
            ResultAvenger.Error(Exception("Not found"))
        }
    }

    override suspend fun setAvengers(avengers: AvengersModel) {
        val avengerList = avengers.data?.results?.transformModelToEntity()
        avengerList?.let { roomDao.createAll(it) }
    }
}
