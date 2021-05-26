package com.example.android.politicalpreparedness.ui.fragment.representative.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.example.android.politicalpreparedness.ui.fragment.representative.model.RepresentativeDiffCallback

class RepresentativeListAdapter: ListAdapter<Representative, RepresentativeViewHolder>(
    RepresentativeDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
        return RepresentativeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

//TODO: Create RepresentativeDiffCallback

//TODO: Create RepresentativeListener