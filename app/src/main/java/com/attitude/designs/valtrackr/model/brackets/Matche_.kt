package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Matche_(
    @SerializedName("id")
    val id: String, // 109711032726168432
    @SerializedName("state")
    val state: String, // completed
    @SerializedName("structuralId")
    val structuralId: String, // match_c50fa51218
    @SerializedName("teams")
    val teams: List<Team_>
) : Parcelable