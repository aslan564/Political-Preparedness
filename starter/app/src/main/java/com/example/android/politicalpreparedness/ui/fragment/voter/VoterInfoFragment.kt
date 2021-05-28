package com.example.android.politicalpreparedness.ui.fragment.voter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.ui.activity.MainActivity
import com.example.android.politicalpreparedness.util.openUrlLocation
import com.example.android.politicalpreparedness.viewModel.VoterInfoViewModel

private const val TAG = "VoterInfoFragment"

class VoterInfoFragment : Fragment() {

    private val binding by lazy { FragmentVoterInfoBinding.inflate(layoutInflater) }
    private lateinit var viewModel: VoterInfoViewModel
    private lateinit var viewModelFactory: VoterInfoViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        viewModelFactory = VoterInfoViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(
            this@VoterInfoFragment,
            viewModelFactory
        ).get(VoterInfoViewModel::class.java)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = this@VoterInfoFragment

        binding.viewModelVoter = viewModel


        viewModel.electionVoterResponse.observe(viewLifecycleOwner, { response ->
            response?.let {
                response.state?.let {list->
                    for (item in list) {
                        binding.stateLocations.setOnClickListener {
                            if (item.electionAdministrationBody?.electionInfoUrl!=null) {
                                Log.d(TAG, "onViewCreated: ${item.electionAdministrationBody.electionInfoUrl}")
                                item.electionAdministrationBody.absenteeVotingInfoUrl?.openUrlLocation(requireActivity())
                            }else{
                                makeToast("Election Not Active")
                            }

                        }
                    }
                }?:makeToast("The time to give this Election is past ")
            }
        })

        viewModel.electionInfoFromDB.observe(viewLifecycleOwner, {
            it?.let {
                if (binding.buttonFollowElection.text == getString(R.string.follow_election)) {
                    binding.buttonFollowElection.setText(R.string.unfollow_election)
                } else {
                    binding.buttonFollowElection.setText(R.string.follow_election)
                }
            }
        })

    }

    private fun makeToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val election = VoterInfoFragmentArgs.fromBundle(it).argElection
            (activity as MainActivity).supportActionBar?.title = election.name


            binding.buttonFollowElection.setOnClickListener {
                if (binding.buttonFollowElection.text == getString(R.string.follow_election)) {
                    viewModel.followElection(election)
                    binding.buttonFollowElection.setText(R.string.unfollow_election)
                } else {
                    viewModel.unfollowElection(election)
                    binding.buttonFollowElection.setText(R.string.follow_election)
                }

            }
            viewModel.getSingleElection(election)
            viewModel.getVoterInfo(election)
        }

    }
    //TODO: Create method to load URL intents

}
