package com.example.android.politicalpreparedness.ui.fragment.launch

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {
    private val binding by lazy { FragmentLaunchBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI(): Unit = with(binding) {
        buttonUpcoming.setOnClickListener {
            navToElections(it)
        }
        buttonFindMyRepresentatives.setOnClickListener{
            navToRepresentatives(it)
        }
    }

    private fun navToElections(view: View) {
        val action = LaunchFragmentDirections.actionLaunchFragmentToElectionFragment()
        view.findNavController().navigate(action)
    }

    private fun navToRepresentatives(view: View) {
        val action = LaunchFragmentDirections.actionLaunchFragmentToRepresentativeFragment()
        view.findNavController().navigate(action)
    }

}
