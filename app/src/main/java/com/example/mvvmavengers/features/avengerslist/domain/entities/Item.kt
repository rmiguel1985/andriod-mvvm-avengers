package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val resourceURI: String? = null,
    val name: String? = null
)
