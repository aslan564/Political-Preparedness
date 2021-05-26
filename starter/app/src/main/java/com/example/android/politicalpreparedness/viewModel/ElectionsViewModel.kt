package com.example.android.politicalpreparedness.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ElectionDatabase(application.applicationContext)

    private val repository = ElectionRepository(database)

    private val _electionList = MutableLiveData<List<Election>>()
    val electionList: LiveData<List<Election>>
        get() = _electionList



    //TODO: Create live data val for upcoming elections
    fun getAllElections() = viewModelScope.launch {
        val data = repository.fetchElectionData()
        when (data.status) {
            LocalStatus.SUCCESS -> {
                data.data?.let { listElection ->
                    val mutableListElections = mutableListOf<Election>()
                    withContext(Dispatchers.Default) {
                        for (item in listElection) {
                            val division = ElectionAdapter.divisionFromJson(item.ocdDivisionId)
                            val date = Converters.fromTimestamp(item.electionDay.localDateToEpoch())
                            mutableListElections.add(convertToElections(item, division, date))
                        }
                    }
                    _electionList.value = mutableListElections
                }
            }
            else -> {
                Log.d(TAG, "ElectionsViewModel: not create")
            }
        }
    }

    //TODO: Create live data val for saved elections
    fun saveElectionsDB(election: Election) = viewModelScope.launch {
        repository.saveElectionsToDB(election)
    }

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    val electionListFromApi=repository.getAllElectionsFromDB()

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun deleteElection(id: Int) = viewModelScope.launch {
        repository.deleteElection(id)
    }

    companion object {
        private const val TAG = "ElectionsViewModel"
    }
}