package com.example.mvvmavengers.features.avengerslist.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class AvengersModel(
    val code: Int? = null,
    val status: String? = null,
    val copyright: String? = null,
    val attributionText: String? = null,
    val attributionHTML: String? = null,
    val etag: String? = null,
    var data: Data? = null

)
