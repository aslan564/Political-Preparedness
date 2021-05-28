package com.example.android.politicalpreparedness.network.models.representative

import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.squareup.moshi.Json

data class Office(
    @Json(name = "divisionId") val divisionId: String?,
    @Json(name = "levels") val levels: List<String>?,
    @Json(name = "name") val name: String?,
    @Json(name = "officialIndices") val officialIndices: List<Int>?,
    @Json(name = "roles") val roles: List<String>?
) {
    fun getRepresentatives(officials: List<Official>): List<Representative> {
        return this.officialIndices?.let {
            it.map { index ->
                Representative(officials[index], this)
            }
        }!!
    }
}
