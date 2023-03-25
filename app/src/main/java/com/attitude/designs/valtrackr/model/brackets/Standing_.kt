package com.attitude.designs.valtrackr.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Standing_(
    @SerializedName("id")
    val id: String, // 109710937834457925
    @SerializedName("name")
    val name: String, // LOCK//IN São Paulo
    @SerializedName("season")
    val season: Season_,
    @SerializedName("slug")
    val slug: String, // lock_in_brazil_2023
    @SerializedName("split")
    val split: Split_X,
    @SerializedName("stages")
    val stages: List<Stage_>
) : Parcelable