package com.example.android.politicalpreparedness.network.models.voter


import com.squareup.moshi.Json

data class Source(
    @Json(name = "name")
    val name: String?,
    @Json(name = "official")
    val official: Boolean?
)