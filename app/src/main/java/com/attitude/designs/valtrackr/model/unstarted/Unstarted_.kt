package com.attitude.designs.valtrackr.model.unstarted


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Unstarted_(
    @SerializedName("events")
    val events: List<UpcomingEvent_>
) : Parcelable