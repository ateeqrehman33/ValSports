package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BracketsResponse_(
    @SerializedName("data")
    val `data`: Data_
) : Parcelable