package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Events(
    val available: Int? = null,
    val collectionURI: String? = null,
    val items: List<Item___>? = null,
    val returned: Int? = null
)
