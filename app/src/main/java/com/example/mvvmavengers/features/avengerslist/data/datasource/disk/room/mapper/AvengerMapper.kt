package com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.mapper

import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.schema.RoomAvengerModel
import com.example.mvvmavengers.features.avengerslist.domain.entities.Result
import com.example.mvvmavengers.features.avengerslist.domain.entities.Thumbnail
import java.util.ArrayList

/**
 * AvengerMapper Class
 *
 *
 * Class to map from room model to avenger model and vice versa
 */
object AvengerMapper {

    /**
     * Convert room model to avenger model
     *
     * @param roomAvengerModel
     * @return Result avenger list
     */
    fun List<RoomAvengerModel>.transformEntityToModel(): List<Result> {
        val avengersList = ArrayList<Result>()

        forEach { roomAvengerModel ->
            val avenger = Result()
            val thumbnail = Thumbnail()

            avenger.id = roomAvengerModel.id
            avenger.name = roomAvengerModel.avengerName
            avenger.description = roomAvengerModel.description
            avenger.modified = roomAvengerModel.avengerDateUpdate
            thumbnail.path = roomAvengerModel.imageUrlPath
            thumbnail.extension = roomAvengerModel.imageUrlExtension
            avenger.thumbnail = thumbnail
            avengersList.add(avenger)
        }

        return avengersList
    }

    /**
     * Convert avenger model to room model list
     *
     * @param results
     * @return RealmAvengerModel list
     */
    fun List<Result>.transformModelToEntity(): List<RoomAvengerModel> {

        val roomAvengerModels = ArrayList<RoomAvengerModel>()

        forEach {
            val roomAvengerModel = RoomAvengerModel()

            roomAvengerModel.id = it.id!!
            roomAvengerModel.avengerName = it.name
            roomAvengerModel.description = it.description
            roomAvengerModel.avengerDateUpdate = it.modified
            roomAvengerModel.imageUrlPath = it.thumbnail!!.path
            roomAvengerModel.imageUrlExtension = it.thumbnail!!.extension
            it.thumbnail = it.thumbnail

            roomAvengerModels.add(roomAvengerModel)
        }
        return roomAvengerModels
    }
}
