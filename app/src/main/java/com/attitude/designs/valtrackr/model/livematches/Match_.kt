package com.attitude.designs.valtrackr.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match_(
    @SerializedName("games")
    val games: List<Game_>,
    @SerializedName("id")
    val id: String, // 109711032726168432
    @SerializedName("strategy")
    val strategy: Strategy_,
    @SerializedName("teams")
    val teams: List<Team_X>
) : Parcelable