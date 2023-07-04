package com.attitude.designs.valtrackr.model.leagues


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplayPriority_(
    @SerializedName("position")
    val position: Int, // 0
    @SerializedName("status")
    val status: String // force_selected
) : Parcelable