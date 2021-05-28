package com.example.android.politicalpreparedness.network.models.representative

import com.squareup.moshi.Json

data class RepresentativeResponse(
    @Json(name = "divisions") val divisions: Divisions?,
    @Json(name = "kind") val kind: String?,
    @Json(name = "normalizedInput") val normalizedInput: NormalizedInput?,
    @Json(name = "offices") val offices: List<Office>?,
    @Json(name = "officials") val officials: List<Official>?
)