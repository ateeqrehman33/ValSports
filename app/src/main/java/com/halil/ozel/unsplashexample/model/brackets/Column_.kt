package com.halil.ozel.unsplashexample.model.brackets


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Column_(
    @SerializedName("cells")
    val cells: List<Cell_>
) : Parcelable