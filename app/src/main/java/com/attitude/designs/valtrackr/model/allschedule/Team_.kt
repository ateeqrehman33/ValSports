package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team_(
    @SerializedName("code")
    val code: String, // ACE
    @SerializedName("image")
    val image: String, // http://static.lolesports.com/teams/1645434356978_ACEND_VECTOR_PURPLE_WITHOUT_TEXT1.png
    @SerializedName("name")
    val name: String, // Acend
    @SerializedName("record")
    val record: Record_,
    @SerializedName("result")
    val result: Result_?
) : Parcelable