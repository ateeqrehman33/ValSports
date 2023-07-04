package com.attitude.designs.valtrackr.model.news


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Entry(
    @SerializedName("article_type")
    val articleType: String, // Normal article
    @SerializedName("banner_settings")
    val bannerSettings: BannerSettings,
    @SerializedName("date")
    val date: String, // 2023-03-27T13:00:00.000Z
    @SerializedName("description")
    val description: String, // Cougar’s chairs will be used on-stage, in training rooms, and on the analyst desks in our Berlin, Istanbul, and Barcelona studios. 
    @SerializedName("external_link")
    val externalLink: String,
    @SerializedName("title")
    val title: String, // VCT EMEA partners with COUGAR as an Official Game Chair Supplier for 2023 and 2024
    @SerializedName("uid")
    val uid: String, // blt090dd92b6026d582
    @SerializedName("url")
    val url: Url,
    @SerializedName("video_link")
    val videoLink: String
) : Parcelable