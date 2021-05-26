package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.RetrofitClient
import com.example.android.politicalpreparedness.network.NetworkResult
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.network.models.election.ElectionPOJO
import com.example.android.politicalpreparedness.network.models.voter.VoterResponse
import java.lang.Exception


class ElectionRepository(database: ElectionDatabase) {

    val retrofitService = RetrofitClient.retrofitService
    val dao = database.electionDao()

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

    suspend fun fetchVoterInfo(address: String, electionId: Long):NetworkResult<VoterResponse> {
        return try {
            val voterInfo = retrofitService.getVoterInfo(address, electionId)
            if (voterInfo.isSuccessful) {
                val voter=voterInfo.body()
                voter?.let {
                    Log.d("VoterInfoViewModel", "1 getVoterInfo: $it")
                    return@let NetworkResult.success(it)
                }?:NetworkResult.error(voterInfo.message()?:"",null)
            }else{
                Log.d("VoterInfoViewModel", "2 getVoterInfo: ${voterInfo.message()}")
                NetworkResult.error(voterInfo.message()?:"",null)
            }

        } catch (ex: Exception) {
            Log.d("VoterInfoViewModel", "3 getVoterInfo: ${ex.message}")
            NetworkResult.error(ex.message?:"No Exception",null)
        }
    }

    suspend fun saveElectionsToDB(election: Election) {
        dao.upsert(election)
    }

    fun getAllElectionsFromDB(): LiveData<List<Election>> {
        return dao.getAllElection()
    }

    suspend fun getSingleElectionsFromDB(id: Int): Election {
        return dao.getSingleElection(id)
    }

    suspend fun deleteElection(id: Int) {
        return dao.deleteElection(id)
    }

    companion object {
        private const val TAG = "ElectionRepositoryImpl"
    }
}