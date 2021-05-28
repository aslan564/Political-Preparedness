package com.example.android.politicalpreparedness.network.models.representative

import com.squareup.moshi.Json

data class Address(
    @Json(name = "city") val city: String?,
    @Json(name = "line1") val line1: String?,
    @Json(name = "line2") val line2: String?,
    @Json(name = "state") val state: String?,
    @Json(name = "zip") val zip: String?
) {
    fun toFormattedString(): String {
        var output = line1.plus("\n")
        if (!line2.isNullOrEmpty()) output = output.plus(line2).plus("\n")
        output = output.plus("$city, $state $zip")
        return output
    }
}