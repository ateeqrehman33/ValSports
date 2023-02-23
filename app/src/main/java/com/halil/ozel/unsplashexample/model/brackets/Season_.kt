package com.halil.ozel.unsplashexample.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season_(
    @SerializedName("endTime")
    val endTime: String, // 2023-12-31T22:59:00Z
    @SerializedName("id")
    val id: String, // 109460648135670300
    @SerializedName("name")
    val name: String, // 2023
    @SerializedName("slug")
    val slug: String, // 2023
    @SerializedName("splits")
    val splits: List<Split_>,
    @SerializedName("startTime")
    val startTime: String, // 2022-12-31T23:00:00Z
    @SerializedName("status")
    val status: String // active
) : Parcelable