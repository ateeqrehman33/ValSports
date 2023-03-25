package com.attitude.designs.valtrackr.model.brackets


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team_X(
    @SerializedName("code")
    val code: String, // GEN
    @SerializedName("id")
    val id: String, // 105665378258561492
    @SerializedName("image")
    val image: String, // http://static.lolesports.com/teams/1678453609404_GenG_logo_200407-051.png
    @SerializedName("name")
    val name: String, // Gen.G 
    @SerializedName("record")
    val record: Record_,
    @SerializedName("ordinal")
    var ordinal: String,
    @SerializedName("slug")
    val slug: String // geng-esports

) : Parcelable