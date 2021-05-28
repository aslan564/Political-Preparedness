package com.example.android.politicalpreparedness.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.Converters
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.LocalStatus
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.network.models.representative.Address
import com.example.android.politicalpreparedness.network.models.representative.Office
import com.example.android.politicalpreparedness.network.models.representative.RepresentativeResponse
import com.example.android.politicalpreparedness.repository.ElectionRepository
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.example.android.politicalpreparedness.util.convertResponseToRepresentative
import com.example.android.politicalpreparedness.util.convertToElections
import com.example.android.politicalpreparedness.util.localDateToEpoch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RepresentativeViewModel"

class RepresentativeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ElectionDatabase(application)
    private val repository = ElectionRepository(database)
    //TODO: Establish live data for representatives and address

    val locations = repository.getLocationFromDB()
    private var _statusResponse = MutableLiveData<LocalStatus>()
    val statusResponse: LiveData<LocalStatus>
        get() = _statusResponse

    private val _allRepresentativeData = MutableLiveData<List<Representative>>()
    val allRepresentativeData: LiveData<List<Representative>>
        get() = _allRepresentativeData

    //TODO: Create function to fetch representatives from API from a provided address


    fun fetchRepresentativeData(address: Address) = viewModelScope.launch {
        if (address.city != null) {
            val data = repository.fetchRepresentativeData(
                address.city,
                true,
                "country",
                "deputyHeadOfGovernment"
            )
            when (data.status) {
                LocalStatus.SUCCESS -> {
                    data.data?.let { representative ->
                        _statusResponse.postValue(LocalStatus.SUCCESS)
                        val convertData = withContext(Dispatchers.Default) {
                            return@withContext convertResponseToRepresentative(representative)
                        }
                        _allRepresentativeData.value = convertData
                    }
                }
                LocalStatus.ERROR -> {
                    _allRepresentativeData.postValue(listOf())
                    _statusResponse.postValue(LocalStatus.ERROR)
                }
                LocalStatus.LOADING -> {
                    _statusResponse.postValue(LocalStatus.LOADING)
                }
            }
        }

    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
