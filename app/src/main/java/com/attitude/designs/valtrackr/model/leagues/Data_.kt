package com.attitude.designs.valtrackr.model.leagues


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data_(
    @SerializedName("leagues")
    val leagues: List<League_>
) : Parcelable