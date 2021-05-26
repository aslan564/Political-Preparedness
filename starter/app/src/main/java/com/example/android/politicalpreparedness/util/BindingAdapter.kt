@file:Suppress("NAME_SHADOWING")

package com.example.android.politicalpreparedness.util

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.R


@BindingAdapter("setDateString")
fun changeButtonText(button: Button,state: Boolean) {
    if (state) {
        button.setText(R.string.follow_election)
    }else
        button.setText(R.string.unfollow_election)

}
