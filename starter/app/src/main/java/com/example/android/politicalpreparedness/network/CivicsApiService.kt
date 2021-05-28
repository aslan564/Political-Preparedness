package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.election.ElectionResponse
import com.example.android.politicalpreparedness.network.models.representative.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.voter.VoterResponse
import retrofit2.Response
import retrofit2.http.*


interface CivicsApiService {
    //TODO: Add elections API Call
    @GET("elections")
    suspend fun getCivicsElections(): Response<ElectionResponse>


    @GET("voterinfo/")
    suspend fun getVoterInfo(
        @Query("address") address: String,
        @Query("electionId") electionId: Long,
        @Query("officialOnly") officialOnly: Boolean = true,
        @Query("returnAllAvailableData") returnAllAvailableData: Boolean = true,
    ): Response<VoterResponse>


    //https://civicinfo.googleapis.com/civicinfo/v2/representatives?address=Alabama%20State%20House%20Election&key=AIzaSyBq52QAE9EE2J8SH8j43hFYfz1pUq21JKU
    @GET("representatives")
    suspend fun getRepresentatives(
        @Query("address") address: String,
        @Query("includeOffices") includeOffices: Boolean=true,
        @Query("levels") levels: String = "country",
        @Query("roles") roles: String = "deputyHeadOfGovernment",
    ): Response<RepresentativeResponse>
    @GET("representatives")
    suspend fun getAllRepresentatives(
        @Query("address") address: String
    ): Response<RepresentativeResponse>
}
