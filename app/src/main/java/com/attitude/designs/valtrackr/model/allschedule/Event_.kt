package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event_(
    @SerializedName("blockName")
    val blockName: String, // Playoffs - Round 1
    @SerializedName("league")
    val league: League_,
    @SerializedName("match")
    val match: Match_,
    @SerializedName("startTime")
    val startTime: String, // 2022-06-19T17:00:00Z
    @SerializedName("state")
    val state: String, // completed
    @SerializedName("tournament")
    val tournament: Tournament_,
    @SerializedName("type")
    val type: String // match
) : Parcelable