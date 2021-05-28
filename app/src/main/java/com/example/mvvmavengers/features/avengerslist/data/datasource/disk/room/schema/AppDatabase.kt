package com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.schema

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmavengers.features.avengerslist.data.datasource.disk.room.dao.ListAvengersDao
import com.example.mvvmavengers.utils.Constants.DATABASE_VERSION

@Database(entities = [RoomAvengerModel::class],
        version = DATABASE_VERSION,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listAvengerDao(): ListAvengersDao
}
