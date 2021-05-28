package com.example.android.politicalpreparedness.ui.fragment.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.network.models.entity.Election
import com.example.android.politicalpreparedness.ui.activity.MainActivity
import com.example.android.politicalpreparedness.ui.fragment.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.ui.fragment.election.adapter.ElectionLocalListAdapter
import com.example.android.politicalpreparedness.viewModel.ElectionsViewModel

private const val TAG = "ElectionsFragment"

class ElectionsFragment : Fragment() {


    private val binding by lazy { FragmentElectionBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ElectionsViewModel
    private lateinit var viewModelFactory: ElectionsViewModelFactory
    private val adapterApi by lazy {
        ElectionListAdapter {
            navigateInformation(it)
        }
    }

    private fun navigateInformation(it: Election) {
        findNavController().navigate(
            ElectionsFragmentDirections.actionElectionsFragmentToVoterFragment(
                it
            )
        )
    }

    private val adapterLocal by lazy {
        ElectionLocalListAdapter {
            navigateInformation(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        viewModelFactory = ElectionsViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(
            this@ElectionsFragment,
            viewModelFactory
        ).get(ElectionsViewModel::class.java)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = this@ElectionsFragment
        binding.recyclerViewApi.apply { adapter = adapterApi }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerViewSaved)
        binding.recyclerViewSaved.apply { adapter = adapterLocal }


        observeLiveData()
    }


    private fun observeLiveData(): Unit = with(viewModel) {
        getAllElections()
        electionList.observe(viewLifecycleOwner, {
            it?.let { listElections ->
                adapterApi.submitList(listElections)
            }
        })
        electionListFromApi.observe(viewLifecycleOwner, {
            it?.let { listElections ->
                adapterLocal.submitList(listElections)
            }
        })

    }
    //TODO: Refresh adapters when fragment loads


    //TODO: Refresh adapters when fragment loads
    private val swipeCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val selectedElections = adapterLocal.currentList.get(position)
                selectedElections?.let {
                    viewModel.deleteElection(it.id)
                }
            }

        }

}