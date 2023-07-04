package com.attitude.designs.valtrackr.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule_(
    @SerializedName("events")
    val events: List<Event_>
) : Parcelable