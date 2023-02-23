package com.halil.ozel.unsplashexample.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Origin_(
    @SerializedName("slot")
    val slot: Int, // 2
    @SerializedName("structuralId")
    val structuralId: String, // seeding
    @SerializedName("type")
    val type: String // seeding
) : Parcelable