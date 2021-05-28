package com.example.android.politicalpreparedness.network.models.representative


import com.squareup.moshi.Json

data class Divisions(
    @Json(name = "ocd-division/country:us")
    val ocdDivisioncountryUs: OcdDivisioncountryUs?,
    @Json(name = "ocd-division/country:us/state:al")
    val ocdDivisioncountryUsstateAl: OcdDivisioncountryUsstateAl?
)