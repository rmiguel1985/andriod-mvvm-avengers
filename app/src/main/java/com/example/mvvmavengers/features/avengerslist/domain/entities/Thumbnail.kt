package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    var path: String? = null,
    var extension: String? = null
)
