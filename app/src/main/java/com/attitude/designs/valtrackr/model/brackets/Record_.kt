package com.attitude.designs.valtrackr.model.brackets


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Record_(
    @SerializedName("losses")
    val losses: Int, // 0
    @SerializedName("ties")
    val ties: Int, // 0
    @SerializedName("wins")
    val wins: Int // 0
) : Parcelable