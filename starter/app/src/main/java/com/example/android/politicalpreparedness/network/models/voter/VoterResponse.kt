package com.example.android.politicalpreparedness.network.models.voter


import com.example.android.politicalpreparedness.network.models.election.ElectionPOJO
import com.squareup.moshi.Json

data class VoterResponse(
    @Json(name = "election") val election: ElectionPOJO?,
    @Json(name = "kind") val kind: String?,
    @Json(name = "normalizedInput") val normalizedInput: NormalizedInput?,
    @Json(name = "state") val state: List<State>?
)