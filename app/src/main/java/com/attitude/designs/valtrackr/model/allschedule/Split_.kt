package com.attitude.designs.valtrackr.model.allschedule


import com.google.gson.annotations.SerializedName

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Split_(
    @SerializedName("name")
    val name: String // vct_challengers_emea_season_2
) : Parcelable