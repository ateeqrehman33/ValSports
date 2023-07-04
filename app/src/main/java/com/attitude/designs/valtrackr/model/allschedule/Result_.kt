package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result_(
    @SerializedName("gameWins")
    val gameWins: Int, // 1
    @SerializedName("outcome")
    val outcome: String? // loss
) : Parcelable