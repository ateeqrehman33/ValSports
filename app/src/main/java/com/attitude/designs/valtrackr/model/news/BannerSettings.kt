package com.attitude.designs.valtrackr.model.news


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerSettings(
    @SerializedName("banner")
    val banner: Banner?
) : Parcelable