package com.example.mvvmavengers.features.avengerslist.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Avenger(
    val avengerName: String,
    val avengerDateUpdate: String,
    val imageUrl: String,
    val description: String
) : Parcelable
