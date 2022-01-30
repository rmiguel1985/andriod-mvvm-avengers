package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Item_(
    var resourceURI: String? = null,
    var name: String? = null
)
