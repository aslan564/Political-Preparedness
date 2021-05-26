package com.example.android.politicalpreparedness.network.models.voter


import com.squareup.moshi.Json

data class NormalizedInput(
    @Json(name = "city")
    val city: String?,
    @Json(name = "line1")
    val line1: String?,
    @Json(name = "state")
    val state: String?,
    @Json(name = "zip")
    val zip: String?
)