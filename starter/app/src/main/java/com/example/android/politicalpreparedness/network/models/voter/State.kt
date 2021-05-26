package com.example.android.politicalpreparedness.network.models.voter


import com.squareup.moshi.Json

data class State(
    @Json(name = "electionAdministrationBody")
    val electionAdministrationBody: ElectionAdministrationBody?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "sources")
    val sources: List<Source>?
)