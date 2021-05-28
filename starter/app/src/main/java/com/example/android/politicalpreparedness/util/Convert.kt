package com.example.android.politicalpreparedness.util

import com.example.android.politicalpreparedness.network.models.entity.Division
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.network.models.election.ElectionPOJO
import com.example.android.politicalpreparedness.network.models.entity.RepresentativeLocation
import com.example.android.politicalpreparedness.network.models.representative.RepresentativeResponse
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

fun convertToElections(electionPOJO: ElectionPOJO, division: Division, date: Date?): Election {
    return Election(
        name = electionPOJO.name,
        division = division,
        electionDay = date!!,
        id = electionPOJO.id.toInt()
    )
}

fun MutableList<Election>.convertToLocation(): List<RepresentativeLocation> = map {
    return@map RepresentativeLocation(
        id = it.id,
        name = it.name
    )
}

fun convertResponseToRepresentative(
    response: RepresentativeResponse,
): List<Representative> {
    val listRepresentative=ArrayList<Representative>()
    if (response.offices != null && response.officials != null) {
        for (item in response.offices.indices) {
         listRepresentative.add(Representative(response.officials[item], response.offices[item]))
        }
    }
    return listRepresentative
}

fun String.localDateToEpoch(): Long {
    return LocalDate.parse(this).toEpochDay()
}

object Constants {
    const val WORK_NAME = "RefreshDataWorker"
}
