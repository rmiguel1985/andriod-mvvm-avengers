package com.example.mvvmavengers.features.avengerslist.domain.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Item___(
    @SerializedName("resourceURI")
    @Expose
    var resourceURI: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null
)
