package com.attitude.designs.valtrackr.model.allschedule


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Record_(
    @SerializedName("losses")
    val losses: Int, // 2
    @SerializedName("wins")
    val wins: Int // 0
) : Parcelable