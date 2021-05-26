package com.example.android.politicalpreparedness.ui.fragment.representative.model

import androidx.recyclerview.widget.DiffUtil
import com.example.android.politicalpreparedness.network.models.representative.Office
import com.example.android.politicalpreparedness.network.models.representative.Official

data class Representative (
    val official: Official,
    val office: Office
)
class RepresentativeDiffCallback : DiffUtil.ItemCallback<Representative>() {
        override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
                return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
                return oldItem.office == newItem.office
        }
}