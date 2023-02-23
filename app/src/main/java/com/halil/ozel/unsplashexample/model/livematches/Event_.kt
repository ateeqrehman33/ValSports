package com.halil.ozel.unsplashexample.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event_(
    @SerializedName("blockName")
    val blockName: String, // Groups
    @SerializedName("id")
    val id: String, // 109711032726168432
    @SerializedName("league")
    val league: League_,
    @SerializedName("match")
    val match: Match_,
    @SerializedName("startTime")
    val startTime: String, // 2023-02-13T17:00:00Z
    @SerializedName("streams")
    val streams: List<Stream_>,
    @SerializedName("tournament")
    val tournament: Tournament_,
    @SerializedName("type")
    val type: String // match
) : Parcelable