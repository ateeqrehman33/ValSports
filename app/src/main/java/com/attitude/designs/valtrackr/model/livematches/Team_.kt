package com.attitude.designs.valtrackr.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team_(
    @SerializedName("id")
    val id: String, // 105665339248060878
    @SerializedName("side")
    val side: String // blue
) : Parcelable