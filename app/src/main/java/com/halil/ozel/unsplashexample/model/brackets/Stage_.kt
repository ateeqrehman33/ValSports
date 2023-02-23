package com.halil.ozel.unsplashexample.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stage_(
    @SerializedName("name")
    val name: String, // Groups
    @SerializedName("sections")
    val sections: List<Section_>,
    @SerializedName("slug")
    val slug: String // groups
) : Parcelable