package com.example.android.politicalpreparedness.network.models.voter


import com.squareup.moshi.Json

data class ElectionAdministrationBody(
    @Json(name = "absenteeVotingInfoUrl")
    val absenteeVotingInfoUrl: String?,
    @Json(name = "ballotInfoUrl")
    val ballotInfoUrl: String?,
    @Json(name = "electionInfoUrl")
    val electionInfoUrl: String?,
    @Json(name = "electionRegistrationConfirmationUrl")
    val electionRegistrationConfirmationUrl: String?,
    @Json(name = "electionRegistrationUrl")
    val electionRegistrationUrl: String?,
    @Json(name = "electionRulesUrl")
    val electionRulesUrl: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "physicalAddress")
    val physicalAddress: PhysicalAddress?
)