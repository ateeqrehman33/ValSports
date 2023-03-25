package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match_(
    @SerializedName("flags")
    val flags: List<String>,
    @SerializedName("id")
    val id: String, // 108272982823679536
    @SerializedName("strategy")
    val strategy: Strategy_,
    @SerializedName("teams")
    val teams: List<Team_>
) : Parcelable