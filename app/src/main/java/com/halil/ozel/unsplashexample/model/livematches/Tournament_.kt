package com.halil.ozel.unsplashexample.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tournament_(
    @SerializedName("id")
    val id: String // 109710937834457925
) : Parcelable