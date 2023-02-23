package com.halil.ozel.unsplashexample.model.unstarted


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data_(
    @SerializedName("inProgress")
    val inProgress: InProgress_,
    @SerializedName("unstarted")
    val unstarted: Unstarted_
) : Parcelable