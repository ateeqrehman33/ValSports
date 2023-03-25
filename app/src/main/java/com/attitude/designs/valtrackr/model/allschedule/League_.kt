package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League_(
    @SerializedName("image")
    val image: String, // http://static.lolesports.com/leagues/1674218324373_VCT_Spark_RGB_Red1.png
    @SerializedName("name")
    val name: String, // VCT EMEA
    @SerializedName("region")
    val region: String, // EMEA
    @SerializedName("slug")
    val slug: String // challengers_emeaa
) : Parcelable