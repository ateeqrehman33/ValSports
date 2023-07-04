package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AllScheduleResponse_(
    @SerializedName("data")
    val `data`: Data_
) : Parcelable