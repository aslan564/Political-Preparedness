package com.example.android.politicalpreparedness.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.Converters
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.LocalStatus
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import com.example.android.politicalpreparedness.util.convertToElections
import com.example.android.politicalpreparedness.util.localDateToEpoch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "VoterInfoViewModel"

class VoterInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ElectionDatabase(application.applicationContext)
    private var dataSource = ElectionRepository(database)


    private var _electionInfo = MutableLiveData<Election>()
    val electionInfo: LiveData<Election>
        get() = _electionInfo
    private var _electionInfoFromDB = MutableLiveData<Election>()
    val electionInfoFromDB: LiveData<Election>
        get() = _electionInfoFromDB

    fun getSingleElection(election: Election) = viewModelScope.launch {
        val data = dataSource.getSingleElectionsFromDB(election.id)
        _electionInfoFromDB.value = data
    }

    //TODO: Add var and methods to populate voter info
    fun followElection(election: Election) = viewModelScope.launch {
        dataSource.saveElectionsToDB(election)
    }

    fun unfollowElection(election: Election) = viewModelScope.launch {
        dataSource.deleteElection(election.id)
    }
    //TODO: Add var and methods to support loading URLs

    fun getVoterInfo(election: Election) = viewModelScope.launch {
        val data = dataSource.fetchVoterInfo(election.name, election.id.toLong())
        if (data.status == LocalStatus.SUCCESS) {
           val convertData= withContext(Dispatchers.Default) {
                val division =
                    ElectionAdapter.divisionFromJson(data.data!!.election!!.ocdDivisionId)
                val date =
                    Converters.fromTimestamp(data.data.election!!.electionDay.localDateToEpoch())
               return@withContext convertToElections(data.data.election, division, date)
            }
            _electionInfo.value = convertData
        } else {
            Log.d(TAG, "getVoterInfo: ${data.msg}")
        }
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}