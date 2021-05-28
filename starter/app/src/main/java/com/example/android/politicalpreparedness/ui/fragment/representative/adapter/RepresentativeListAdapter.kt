package com.example.android.politicalpreparedness.ui.fragment.representative.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.example.android.politicalpreparedness.ui.fragment.representative.model.RepresentativeDiffCallback

class RepresentativeListAdapter(private val onClickRepresentative: (String) -> Unit) :
    ListAdapter<Representative, RepresentativeViewHolder>(
        RepresentativeDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
        return RepresentativeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickRepresentative)
    }
}

//TODO: Create RepresentativeDiffCallback

//TODO: Create RepresentativeListener