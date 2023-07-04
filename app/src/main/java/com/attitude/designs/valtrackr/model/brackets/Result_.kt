package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result_(
    @SerializedName("gameWins")
    val gameWins: Int, // 2
    @SerializedName("outcome")
    val outcome: String? // win
) : Parcelable