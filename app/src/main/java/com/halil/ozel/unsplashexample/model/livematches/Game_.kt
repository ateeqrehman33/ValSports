package com.halil.ozel.unsplashexample.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game_(
    @SerializedName("id")
    val id: String, // 109711032726168433
    @SerializedName("number")
    val number: Int, // 1
    @SerializedName("state")
    val state: String, // completed
    @SerializedName("teams")
    val teams: List<Team_>
) : Parcelable