package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Stories(
    var available: Int? = null,
    var collectionURI: String? = null,
    var items: List<Item__>? = null,
    var returned: Int? = null
)
