package com.halil.ozel.unsplashexample.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tournament_(
    @SerializedName("season")
    val season: Season_,
    @SerializedName("split")
    val split: Split_
) : Parcelable