package com.attitude.designs.valtrackr.model.livematches


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stream_(
    @SerializedName("countries")
    val countries: List<String>,
    @SerializedName("locale")
    val locale: String, // uk-UA
    @SerializedName("mediaLocale")
    val mediaLocale: MediaLocale_,
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("parameter")
    val parameter: String, // valorantmc_ua
    @SerializedName("provider")
    val provider: String // twitch
) : Parcelable