package com.attitude.designs.valtrackr.model.unstarted


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team_(
    @SerializedName("code")
    val code: String, // LPB
    @SerializedName("image")
    val image: String // http://static.lolesports.com/teams/1673468540144_teamlogo.png
) : Parcelable