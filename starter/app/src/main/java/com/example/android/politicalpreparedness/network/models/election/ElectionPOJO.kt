package com.example.android.politicalpreparedness.network.models.election


import com.squareup.moshi.Json


data class ElectionPOJO(
    @Json(name = "electionDay") var electionDay: String,
    @Json(name = "id") var id: String,
    @Json(name = "name") var name: String,
    @Json(name = "ocdDivisionId") var ocdDivisionId: String
)