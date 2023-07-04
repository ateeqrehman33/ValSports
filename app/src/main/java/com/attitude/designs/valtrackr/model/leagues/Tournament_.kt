package com.attitude.designs.valtrackr.model.leagues


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tournament_(
    @SerializedName("id")
    val id: String, // 109710937834457925
    @SerializedName("season")
    val season: Season_?,
    @SerializedName("split")
    val split: Split_X?
) : Parcelable