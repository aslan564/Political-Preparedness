package com.example.android.politicalpreparedness.ui.fragment.representative.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemLayoutRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.representative.Channel
import com.example.android.politicalpreparedness.ui.fragment.representative.model.Representative

class RepresentativeViewHolder private constructor(private val binding: ItemLayoutRepresentativeBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Representative) {
       /* binding.representative = item
        binding.representativePhoto.setImageResource(R.drawable.ic_profile)

        //TODO: Show social links ** Hint: Use provided helper methods
        //TODO: Show www link ** Hint: Use provided helper methods
*/
        binding.executePendingBindings()
    }
    companion object{
        fun from(parent: ViewGroup): RepresentativeViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val view = ItemLayoutRepresentativeBinding.inflate(inflate, parent, false)
            return RepresentativeViewHolder(view)
        }
    }

    //TODO: Add companion object to inflate ViewHolder (from)

    private fun showSocialLinks(channels: List<Channel>) {
     /*   val facebookUrl = getFacebookUrl(channels)
        if (!facebookUrl.isNullOrBlank()) { enableLink(binding.facebookIcon, facebookUrl) }

        val twitterUrl = getTwitterUrl(channels)
        if (!twitterUrl.isNullOrBlank()) { enableLink(binding.twitterIcon, twitterUrl) }*/
    }

    private fun showWWWLinks(urls: List<String>) {
       // enableLink(binding.wwwIcon, urls.first())
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