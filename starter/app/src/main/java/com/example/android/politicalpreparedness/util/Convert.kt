package com.example.android.politicalpreparedness.util

import com.example.android.politicalpreparedness.network.models.entity.Division
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.network.models.election.ElectionPOJO
import java.time.LocalDate
import java.util.*

fun convertToElections(electionPOJO: ElectionPOJO, division: Division, date: Date?): Election {
    return Election(
        name = electionPOJO.name,
        division = division,
        electionDay = date!!,
        id = electionPOJO.id.toInt()
    )
}
fun String.localDateToEpoch(): Long {
    return LocalDate.parse(this).toEpochDay()
}
/*
fun Long.localDateToEpoch(): String {
    return LocalDate.parse()
}*/
