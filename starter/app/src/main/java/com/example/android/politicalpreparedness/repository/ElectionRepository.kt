package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.RetrofitClient
import com.example.android.politicalpreparedness.network.NetworkResult
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.network.models.election.ElectionPOJO
import com.example.android.politicalpreparedness.network.models.entity.RepresentativeLocation
import com.example.android.politicalpreparedness.network.models.representative.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.voter.VoterResponse
import com.example.android.politicalpreparedness.util.convertToLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


private const val TAG = "ElectionRepository"

class ElectionRepository(database: ElectionDatabase) {

    val retrofitService = RetrofitClient.retrofitService
    val dao = database.electionDao()

    //3101313
    suspend fun fetchRepresentativeData(
        address: String,
        includeOffice: Boolean,
        levels: String,
        roles: String
    ): NetworkResult<RepresentativeResponse> {
        return try {
            val response = retrofitService.getAllRepresentatives(address)
            if (response.isSuccessful) {
                val data = response.body()
                data?.let {
                    Log.d(TAG, "fetchRepresentativeData 1 : $it")
                    return@let NetworkResult.success(it)
                } ?: NetworkResult.error(response.message() ?: "No data ", null)
            } else {
                Log.d(TAG, "fetchRepresentativeData 2 : ${response.message()}")
                NetworkResult.error("${response.errorBody().toString()} ${response.code()}", null)
            }
        } catch (e: Exception) {
            Log.d(TAG, "fetchRepresentativeData 3 : ${e.message}")
            NetworkResult.error(e.localizedMessage ?: " Exception No data ", null)
        }
    }

    suspend fun fetchElectionData(): NetworkResult<List<ElectionPOJO>> {
        return try {
            val response = retrofitService.getCivicsElections()
            if (response.isSuccessful && response.code() == 200) {
                val data = response.body()
                data?.let {
                    return@let NetworkResult.success(it.elections)
                } ?: NetworkResult.error(response.message() ?: "No data ", null)
            } else {
                NetworkResult.error("${response.errorBody().toString()} ${response.code()}", null)
            }
        } catch (e: Exception) {
            NetworkResult.error(e.localizedMessage ?: " Exception No data ", null)
        }
    }

    suspend fun fetchVoterInfo(address: String, electionId: Long): NetworkResult<VoterResponse> {
        return try {
            val voterInfo = retrofitService.getVoterInfo(address, electionId)
            if (voterInfo.isSuccessful) {
                val voter = voterInfo.body()
                voter?.let {
                    Log.d("VoterInfoViewModel", "1 getVoterInfo: $it")
                    return@let NetworkResult.success(it)
                } ?: NetworkResult.error(voterInfo.message() ?: "", null)
            } else {
                Log.d("VoterInfoViewModel", "2 getVoterInfo: ${voterInfo.message()}")
                NetworkResult.error(voterInfo.message() ?: "", null)
            }

        } catch (ex: Exception) {
            Log.d("VoterInfoViewModel", "3 getVoterInfo: ${ex.message}")
            NetworkResult.error(ex.message ?: "No Exception", null)
        }
    }

    suspend fun saveElectionsToDB(election: Election) {
        dao.upsert(election)
    }


    suspend fun saveLocationToDB(location: MutableList<Election>) {
        val convertData= withContext(Dispatchers.Default){
            return@withContext location.convertToLocation()
        }
        return dao.upsertCity(*convertData.toTypedArray())
    }

    fun getAllElectionsFromDB(): LiveData<List<Election>> {
        return dao.getAllElection()
    }

    fun getLocationFromDB(): LiveData<List<RepresentativeLocation>> {
        return dao.getAllLocation()
    }

    suspend fun getSingleElectionsFromDB(id: Int): Election {
        return dao.getSingleElection(id)
    }

    suspend fun deleteElection(id: Int) {
        return dao.deleteElection(id)
    }

}