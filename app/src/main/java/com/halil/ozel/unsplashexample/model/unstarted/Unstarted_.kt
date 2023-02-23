package com.halil.ozel.unsplashexample.model.unstarted


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.halil.ozel.unsplashexample.model.livematches.Event_
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Unstarted_(
    @SerializedName("events")
    val events: List<UpcomingEvent_>
) : Parcelable