package com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvmavengers.utils.Constants.DATABASE_AVENGER_TABLE_NAME

@Entity(tableName = DATABASE_AVENGER_TABLE_NAME)
data class RoomAvengerModel(
    @PrimaryKey
    var id: Int = 0,
    var avengerName: String? = null,
    var avengerDateUpdate: String? = null,
    var imageUrlPath: String? = null,
    var imageUrlExtension: String? = null,
    var description: String? = null,
    var thumbnail: String? = null
)
