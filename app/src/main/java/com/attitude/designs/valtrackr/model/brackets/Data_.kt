package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data_(
    @SerializedName("standings")
    val standings: List<Standing_>
) : Parcelable