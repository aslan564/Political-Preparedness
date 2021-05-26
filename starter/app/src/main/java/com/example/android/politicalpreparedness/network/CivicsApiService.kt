package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.election.ElectionResponse
import com.example.android.politicalpreparedness.network.models.voter.VoterResponse
import retrofit2.Response
import retrofit2.http.*


interface CivicsApiService {
    //TODO: Add elections API Call
    @GET("elections")
    suspend fun getCivicsElections(): Response<ElectionResponse>


    /*
     'https://civicinfo.googleapis.com/civicinfo/v2/voterinfo?address=alabama&electionId=2000&officialOnly=true&returnAllAvailableData=true&key=[YOUR_API_KEY]' \
     --header 'Accept: application/json' \
     --compressed
    */

    //https://civicinfo.googleapis.com/civicinfo/v2/voterinfo?address=alabama&electionId=2000&key=AIzaSyBq52QAE9EE2J8SH8j43hFYfz1pUq21JKU
    //TODO: Add voterinfo API Call


    @GET("voterinfo/")
    suspend fun getVoterInfo(
        @Query("address") address: String,
        @Query("electionId") electionId: Long,
        @Query("officialOnly") officialOnly: Boolean=true,
        @Query("returnAllAvailableData") returnAllAvailableData: Boolean=true,
    ): Response<VoterResponse>

    //TODO: Add representatives API Call "aslanovaslan165@gmail.com"


}
