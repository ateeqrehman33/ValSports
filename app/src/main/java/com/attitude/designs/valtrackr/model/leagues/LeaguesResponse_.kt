package com.attitude.designs.valtrackr.model.leagues


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeaguesResponse_(
    @SerializedName("data")
    val `data`: Data_
) : Parcelable