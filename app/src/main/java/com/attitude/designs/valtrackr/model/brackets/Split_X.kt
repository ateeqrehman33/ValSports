package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Split_X(
    @SerializedName("id")
    val id: String // 109710902902203116
) : Parcelable