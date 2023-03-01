package com.halil.ozel.unsplashexample.model.leagues


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League_(
    @SerializedName("displayPriority")
    val displayPriority: DisplayPriority_,
    @SerializedName("id")
    val id: String, // 109551178413356399
    @SerializedName("image")
    val image: String, // http://static.lolesports.com/leagues/1671618316438_600px-VCT_LOCK_IN_Brasil_full_lightmode1.png
    @SerializedName("name")
    val name: String, // VCT LOCK//IN
    @SerializedName("priority")
    val priority: Int, // 1000
    @SerializedName("region")
    val region: String, // INTERNATIONAL
    @SerializedName("slug")
    val slug: String, // vct_lock_in
    @SerializedName("tournaments")
    val tournaments: List<Tournament_>
) : Parcelable