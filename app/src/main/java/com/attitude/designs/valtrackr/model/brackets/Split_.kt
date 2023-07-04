package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Split_(
    @SerializedName("endTime")
    val endTime: String, // 2023-04-03T22:00:00Z
    @SerializedName("id")
    val id: String, // 109460712659577070
    @SerializedName("name")
    val name: String, // emea_regional_leagues_split_1
    @SerializedName("slug")
    val slug: String, // emea_regional_leagues_split_1
    @SerializedName("startTime")
    val startTime: String // 2022-12-31T23:00:00Z
) : Parcelable