package com.example.android.politicalpreparedness.ui.fragment.election

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.viewModel.ElectionsViewModel
import java.lang.IllegalArgumentException

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
@Suppress("UNCHECKED_CAST")
class ElectionsViewModelFactory(private val application: Application):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
            return ElectionsViewModel(application) as T
        }
        else{
            throw IllegalArgumentException("Unable to constructor viewmodel")
        }
    }

}