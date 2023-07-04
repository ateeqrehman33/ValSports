package com.attitude.designs.valtrackr.model.unstarted


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingEvent_(
    @SerializedName("league")
    val league: League_,
    @SerializedName("match")
    val match: Match_,
    @SerializedName("startTime")
    val startTime: String // 2023-02-20T17:00:00Z
) : Parcelable