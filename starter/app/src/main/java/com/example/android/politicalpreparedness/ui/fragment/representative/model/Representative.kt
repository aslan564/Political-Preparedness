package com.example.android.politicalpreparedness.ui.fragment.representative.model

import androidx.recyclerview.widget.DiffUtil
import com.example.android.politicalpreparedness.network.models.representative.Office
import com.example.android.politicalpreparedness.network.models.representative.Official

class Representative(var official: Official, var office: Office) {

    companion object {
        fun addOfficialList(response: List<Official>): ArrayList<Official> {
            val newList = ArrayList<Official>()
            newList.addAll(response)
            return newList
        }


        fun addOOfficeList(response: List<Office>): ArrayList<Office> {
            val newList = ArrayList<Office>()
            newList.addAll(response)
            return newList
        }
    }

}

class RepresentativeDiffCallback : DiffUtil.ItemCallback<Representative>() {
    override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem.office == newItem.office
    }
}