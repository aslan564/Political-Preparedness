package com.example.android.politicalpreparedness.network.models.representative


import com.squareup.moshi.Json

data class OcdDivisioncountryUsstateAl(
    @Json(name = "name")
    val name: String?,
    @Json(name = "officeIndices")
    val officeIndices: List<Int>?
)