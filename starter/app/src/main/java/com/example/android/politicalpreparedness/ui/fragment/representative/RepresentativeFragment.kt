package com.example.android.politicalpreparedness.ui.fragment.representative

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.LocalStatus
import com.example.android.politicalpreparedness.network.models.entity.RepresentativeLocation
import com.example.android.politicalpreparedness.network.models.representative.Address
import com.example.android.politicalpreparedness.ui.fragment.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.example.android.politicalpreparedness.viewModel.RepresentativeViewModel
import java.util.Locale

private const val TAG = "RepresentativeFragment"

class DetailFragment : Fragment() {

    private val binding by lazy { FragmentRepresentativeBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<RepresentativeViewModel>()

    private val adapter by lazy { RepresentativeListAdapter(onClickRepresentative = this::onClickRepresentative) }
    private var city_name: String = ""

    companion object {
        //TODO: Add Constant for Location request
    }

    //TODO: Declare ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    private fun onClickRepresentative(representativeUrl: String) {
        Toast.makeText(requireContext(), representativeUrl, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        binding.recyclerViewRepresentative.apply {
            adapter = this@DetailFragment.adapter
        }
        //TODO: Add ViewModel values and create ViewModel
        viewModel.allRepresentativeData.observe(viewLifecycleOwner, {
            it?.let { representative ->
             adapter.submitList(representative)
            }
        })

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters
    }

    private fun bindUI(): Unit = with(binding) {
        recyclerViewRepresentative.apply {
            adapter = this@DetailFragment.adapter
        }

        viewModel.locations.observe(viewLifecycleOwner, {
            viewModel.locations.observe(viewLifecycleOwner, { locationList ->
                locationList?.let {
                    val listCityName = ArrayList<String>()
                    for (item in locationList) {
                        listCityName.add(item.name)
                    }
                    val spinnerArrayAdapter =
                        ArrayAdapter(
                            requireActivity(),
                            android.R.layout.simple_spinner_item,
                            listCityName
                        )
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    state.adapter = spinnerArrayAdapter
                    spinnerSelectedListener(locationList, state)
                }
            })
        })
        viewModel.statusResponse.observe(viewLifecycleOwner, {
            it?.let {
                createToastMessage(it)
            }
        })
        buttonSearch.setOnClickListener {


            val line1 = addressLine1.text.toString()
            val line2 = addressLine2.text.toString()
            val zip = zip.text.toString()
            val state = city.text.toString()

            if (line1.isNotEmpty() && line2.isNotEmpty() && zip.isNotEmpty() && state.isNotEmpty()) {
                val address = Address(city_name, line1, line2, state, zip)
                viewModel.fetchRepresentativeData(address)
            } else {
                Toast.makeText(
                    requireContext(),
                    "emin olki butun xanalar doludu ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun createToastMessage(it: LocalStatus) {
        when (it) {
            LocalStatus.LOADING -> {
                Toast.makeText(requireContext(), "Please wail", Toast.LENGTH_SHORT).show()
            }
            LocalStatus.ERROR -> {
                Toast.makeText(requireContext(), "Election Not Found", Toast.LENGTH_SHORT).show()
            }
            LocalStatus.SUCCESS -> {
                Log.d(TAG, "createToastMessage: election tapildi ")
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //TODO: Handle location permission result to get location on permission granted
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            //TODO: Request Location permissions
            false
        }
    }

    private fun isPermissionGranted(): Boolean {
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
        return false
    }

    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun spinnerSelectedListener(
        levels: List<RepresentativeLocation>,
        state: Spinner
    ) {
        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!levels.isNullOrEmpty()) {
                    if (view != null) {
                        city_name = levels[position].name
                        binding.buttonSearch.isEnabled = true
                    }
                    //	setupGridAdapter(FileSelected.fileInPath(pathList[position]))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "please select anythings", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

}