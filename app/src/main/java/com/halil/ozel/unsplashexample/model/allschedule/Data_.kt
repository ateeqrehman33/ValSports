package com.halil.ozel.unsplashexample.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data_(
    @SerializedName("schedule")
    val schedule: Schedule_
) : Parcelable