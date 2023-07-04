package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cell_(
    @SerializedName("matches")
    val matches: List<Matche_>,
    @SerializedName("name")
    val name: String, // Round 1
    @SerializedName("slug")
    val slug: String // round_1
) : Parcelable