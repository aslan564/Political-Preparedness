package com.example.android.politicalpreparedness.ui.fragment.voter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.viewModel.VoterInfoViewModel
import java.lang.IllegalArgumentException

//TODO: Create Factory to generate VoterInfoViewModel with provided election datasource
class VoterInfoViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(application) as T
        }else{
            throw IllegalArgumentException("Unable to constructor viewmodel")
        }
    }

}