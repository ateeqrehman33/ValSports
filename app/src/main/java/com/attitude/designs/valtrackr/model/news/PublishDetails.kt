package com.attitude.designs.valtrackr.model.news


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PublishDetails(
    @SerializedName("environment")
    val environment: String, // blt0975f986d879d8cb
    @SerializedName("locale")
    val locale: String, // en-gb
    @SerializedName("time")
    val time: String, // 2023-03-27T13:00:00.043Z
    @SerializedName("user")
    val user: String // blt14ffa27ee1cfff8f
) : Parcelable