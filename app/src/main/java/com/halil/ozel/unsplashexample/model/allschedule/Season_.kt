package com.halil.ozel.unsplashexample.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season_(
    @SerializedName("name")
    val name: String // 2022
) : Parcelable