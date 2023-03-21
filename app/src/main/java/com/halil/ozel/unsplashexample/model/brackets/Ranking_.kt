package com.halil.ozel.unsplashexample.model.brackets


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ranking_(
    @SerializedName("ordinal")
    val ordinal: Int, // 1
    @SerializedName("teams")
    val teams: List<Team_X>
) : Parcelable