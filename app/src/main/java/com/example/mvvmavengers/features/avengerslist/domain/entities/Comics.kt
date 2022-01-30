package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comics(
    val available: Int? = null,
    val collectionURI: String? = null,
    val items: List<Item>? = null,
    val returned: Int? = null
)
