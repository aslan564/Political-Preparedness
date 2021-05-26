package com.example.android.politicalpreparedness.network.models.representative


import com.squareup.moshi.Json

data class Channel(
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?
)