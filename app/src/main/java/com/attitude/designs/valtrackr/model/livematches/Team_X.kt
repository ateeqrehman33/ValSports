package com.attitude.designs.valtrackr.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team_X(
    @SerializedName("code")
    val code: String, // NRG
    @SerializedName("id")
    val id: String, // 105665339248060878
    @SerializedName("image")
    val image: String, // http://static.lolesports.com/teams/nrg-on-dark.png
    @SerializedName("name")
    val name: String, // NRG
    @SerializedName("record")
    val record: Record_,
    @SerializedName("result")
    val result: Result_
) : Parcelable