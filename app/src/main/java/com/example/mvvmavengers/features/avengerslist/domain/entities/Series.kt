package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Series(
    var available: Int? = null,
    var collectionURI: String? = null,
    var items: List<Item_>? = null,
    var returned: Int? = null
)
