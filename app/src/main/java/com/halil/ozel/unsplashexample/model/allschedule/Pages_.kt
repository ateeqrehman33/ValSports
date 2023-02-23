package com.halil.ozel.unsplashexample.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pages_(
    @SerializedName("newer")
    val newer: String, // null
    @SerializedName("older")
    val older: String // b2xkZXI6OjEwODI3Mjk4MjgyMzY3OTUzNg==
) : Parcelable