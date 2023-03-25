package com.attitude.designs.valtrackr.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Record_(
    @SerializedName("losses")
    val losses: Int, // 0
    @SerializedName("wins")
    val wins: Int // 0
) : Parcelable