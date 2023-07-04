package com.attitude.designs.valtrackr.model.leagues


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Split_(
    @SerializedName("id")
    val id: String, // 109460712659577070
    @SerializedName("name")
    val name: String, // emea_regional_leagues_split_1
    @SerializedName("slug")
    val slug: String // emea_regional_leagues_split_1
) : Parcelable