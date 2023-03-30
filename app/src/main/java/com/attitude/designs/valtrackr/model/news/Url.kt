package com.attitude.designs.valtrackr.model.news


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Url(
    @SerializedName("overridden")
    val overridden: Boolean, // false
    @SerializedName("url")
    val url: String, // /news/vct-emea-partners-with-cougar-as-an-official-game-chair-supplier-for-2023-and-2024/
    @SerializedName("useLangSub")
    val useLangSub: Boolean // false
) : Parcelable