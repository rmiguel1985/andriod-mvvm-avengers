package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val offset: Int? = null,
    val limit: Int? = null,
    val total: Int? = null,
    val count: Int? = null,
    var results: List<Result>? = null
)
