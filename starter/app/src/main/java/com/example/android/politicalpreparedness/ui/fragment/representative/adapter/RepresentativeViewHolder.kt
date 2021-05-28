package com.example.android.politicalpreparedness.ui.fragment.representative.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemLayoutRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.representative.Channel
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative
import com.example.android.politicalpreparedness.util.loadImageWithPicasso
import com.example.android.politicalpreparedness.util.openUrlLocation

class RepresentativeViewHolder private constructor(private val binding: ItemLayoutRepresentativeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Representative, onClickRepresentative: (String) -> Unit): Unit =
        with(binding) {
            representative = item
            loadImageWithPicasso(imageViewCandidate, item.official.photoUrl)
            item.official.channels?.let { showSocialLinks(it) }
            //TODO: Show social links ** Hint: Use provided helper methods
            //TODO: Show www link ** Hint: Use provided helper methods
            for (url in item.official.channels!!) {
                Log.d("Representative", "bind: ${url.type}")
                Log.d("Representative", "bind: ${url.id}")

            }

            imageViewCandidateFacebook.setOnClickListener {
                item.official.channels?.let {
                    onClickRepresentative(getFacebookUrl(item.official.channels!!)!!)
                }
            }
            imageViewCandidateWww.setOnClickListener {
                item.official.channels?.let {
                    onClickRepresentative(getFacebookUrl(item.official.channels!!)!!)
                }
            }
            imageViewCandidateTwitter.setOnClickListener {
                item.official.channels?.let {
                    onClickRepresentative(getTwitterUrl(item.official.channels!!)!!)
                }
            }

            executePendingBindings()
        }

    companion object {
        fun from(parent: ViewGroup): RepresentativeViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val view = ItemLayoutRepresentativeBinding.inflate(inflate, parent, false)
            return RepresentativeViewHolder(view)
        }
    }

    //TODO: Add companion object to inflate ViewHolder (from)

    private fun showSocialLinks(channels: List<Channel>): Unit = with(binding) {
        val facebookUrl = getFacebookUrl(channels)
        if (!facebookUrl.isNullOrBlank()) {
            enableLink(imageViewCandidateFacebook, facebookUrl)
        }

        val twitterUrl = getTwitterUrl(channels)
        if (!twitterUrl.isNullOrBlank()) {
            enableLink(imageViewCandidateTwitter, twitterUrl)
        }

    }

    private fun showWWWLinks(urls: List<String>) {
        enableLink(binding.imageViewCandidateWww, urls.first())
    }

    private fun getFacebookUrl(channels: List<Channel>): String? {
        return channels.filter { channel -> channel.type == "Facebook" }
            .map { channel -> "https://www.facebook.com/${channel.id}" }
            .firstOrNull()
    }

    private fun getTwitterUrl(channels: List<Channel>): String? {
        return channels.filter { channel -> channel.type == "Twitter" }
            .map { channel -> "https://www.twitter.com/${channel.id}" }
            .firstOrNull()
    }

    private fun enableLink(view: ImageView, url: String) {
        view.visibility = View.VISIBLE
        view.setOnClickListener { setIntent(url) }
    }

    private fun setIntent(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        itemView.context.startActivity(intent)
    }

}