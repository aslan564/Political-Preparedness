package com.example.android.politicalpreparedness.network.models.representative

import com.squareup.moshi.Json


data class Official(
    @Json(name = "address") val address: List<Address>?,
    @Json(name = "channels") val channels: List<Channel>?,
    @Json(name = "emails") val emails: List<String>?,
    @Json(name = "name") val name: String?,
    @Json(name = "party") val party: String?,
    @Json(name = "phones") val phones: List<String>?,
    @Json(name = "photoUrl") val photoUrl: String?,
    @Json(name = "urls") val urls: List<String>?
)