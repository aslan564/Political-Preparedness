package com.example.android.politicalpreparedness.ui.fragment.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemLayoutElectionBinding
import com.example.android.politicalpreparedness.network.models.entity.Election

class ElectionViewHolder private constructor(private val binding: ItemLayoutElectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(election: Election, onClickElection: (Election) -> Unit): Unit =
        with(binding) {
            model = election
            root.setOnClickListener {
                onClickElection(election)
            }
            binding.executePendingBindings()
        }

    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLayoutElectionBinding.inflate(inflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}
