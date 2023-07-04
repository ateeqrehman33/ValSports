package com.attitude.designs.valtrackr.model.unstarted


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League_(
    @SerializedName("id")
    val id: String, // 107566795186957938
    @SerializedName("image")
    val image: String, // http://static.lolesports.com/leagues/1672932221970_NORTH_ICON_400_400.png
    @SerializedName("name")
    val name: String, // Challengers Northern Europe
    @SerializedName("slug")
    val slug: String // vrl_northern_europe
) : Parcelable