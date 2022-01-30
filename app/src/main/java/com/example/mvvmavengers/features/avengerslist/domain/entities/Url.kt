package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Url(
    val type: String? = null,
    val url: String? = null
)
