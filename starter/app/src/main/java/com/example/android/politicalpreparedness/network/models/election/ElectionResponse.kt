package com.example.android.politicalpreparedness.network.models.election


import com.squareup.moshi.Json

data class ElectionResponse(
    @Json(name = "elections")
    var elections: List<ElectionPOJO>,
    @Json(name = "kind")
    var kind: String
)