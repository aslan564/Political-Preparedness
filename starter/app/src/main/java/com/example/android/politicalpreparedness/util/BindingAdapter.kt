@file:Suppress("NAME_SHADOWING")

package com.example.android.politicalpreparedness.util

import android.app.Activity
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.politicalpreparedness.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

private const val TAG = "BindingAdapter"

@BindingAdapter("setDateString")
fun changeButtonText(button: Button, state: Boolean) {
    if (state) {
        button.setText(R.string.follow_election)
    } else
        button.setText(R.string.unfollow_election)
}

@BindingAdapter("app:downloadImageFromApi")
fun loadImageWithPicasso(imageView: CircleImageView, url: String?) {
    //Picasso.get().load(url).placeholder(R.drawable.ic_profile).into(imageView)
      Glide.with(imageView).load(url).placeholder(R.drawable.ic_profile).into(imageView)

    Log.d(TAG, "loadImageWithPicasso : $url")
}
fun String.openUrlLocation(activity: Activity){
    val intent=Intent(Intent.ACTION_VIEW, Uri.parse(this))
    activity.startActivity(intent)
}

