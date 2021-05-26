package com.example.android.politicalpreparedness.ui.fragment.voter

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.ui.activity.MainActivity
import com.example.android.politicalpreparedness.viewModel.VoterInfoViewModel

class VoterInfoFragment : Fragment() {

    private val binding by lazy { FragmentVoterInfoBinding.inflate(layoutInflater) }
    private lateinit var viewModel: VoterInfoViewModel
    private lateinit var viewModelFactory: VoterInfoViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: Add ViewModel values and create ViewModel
        binding.lifecycleOwner = this@VoterInfoFragment
        viewModelFactory = VoterInfoViewModelFactory(requireActivity().application)



        viewModel = ViewModelProvider(
            this@VoterInfoFragment,
            viewModelFactory
        ).get(VoterInfoViewModel::class.java)

        binding.viewModelVoter = viewModel
        viewModel.electionInfoFromDB.observe(viewLifecycleOwner, {
            it?.let {
                if (binding.buttonFollowElection.text == ButtonState.FOLLOW) {
                    binding.buttonFollowElection.setText(R.string.unfollow_election)
                } else {
                    binding.buttonFollowElection.setText(R.string.follow_election)
                }
            }
        })


        //TODO: Link elections to voter info

    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val election = VoterInfoFragmentArgs.fromBundle(it).argElection
            (activity as MainActivity).supportActionBar?.title = election.name
            binding.buttonFollowElection.setOnClickListener {
                if (binding.buttonFollowElection.text == ButtonState.FOLLOW) {
                    viewModel.followElection(election)
                    binding.buttonFollowElection.setText(R.string.unfollow_election)
                    Toast.makeText(requireContext(), "Follow", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.unfollowElection(election)
                    binding.buttonFollowElection.setText(R.string.follow_election)
                    Toast.makeText(requireContext(), "Unfollow", Toast.LENGTH_SHORT).show()
                }

            }
            viewModel.getSingleElection(election)
            viewModel.getVoterInfo(election)
        }

    }
    //TODO: Create method to load URL intents

}

object ButtonState {
    const val FOLLOW = "Follow Election"
    const val UNFOLLOW = "Unfollow Election"
}