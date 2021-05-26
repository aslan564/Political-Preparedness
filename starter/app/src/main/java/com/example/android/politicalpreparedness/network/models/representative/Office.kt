package com.example.android.politicalpreparedness.network.models.representative

import com.example.android.politicalpreparedness.network.models.entity.Division
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.squareup.moshi.Json

data class Office (
    val name: String,
    @Json(name="divisionId") val division: Division,
    @Json(name="officialIndices") val officials: List<Int>
) {
    fun getRepresentatives(officials: List<Official>): List<Representative> {
        return this.officials.map { index ->
            Representative(officials[index], this)
        }
    }
}
