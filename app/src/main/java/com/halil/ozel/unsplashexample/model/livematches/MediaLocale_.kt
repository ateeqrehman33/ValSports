package com.halil.ozel.unsplashexample.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaLocale_(
    @SerializedName("englishName")
    val englishName: String, // Ukrainian
    @SerializedName("locale")
    val locale: String, // uk-UA
    @SerializedName("translatedName")
    val translatedName: String // українська
) : Parcelable