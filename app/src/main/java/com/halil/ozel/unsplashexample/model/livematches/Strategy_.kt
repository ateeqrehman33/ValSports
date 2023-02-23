package com.halil.ozel.unsplashexample.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Strategy_(
    @SerializedName("count")
    val count: Int, // 3
    @SerializedName("type")
    val type: String // bestOf
) : Parcelable