package com.halil.ozel.unsplashexample.model.brackets


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Section_(
    @SerializedName("columns")
    val columns: List<Column_>,
    @SerializedName("rankings")
    val rankings: List<Ranking_>,
    @SerializedName("id")
    val id: String, // 109710937837996871
    @SerializedName("name")
    val name: String, // Group A
    @SerializedName("type")
    val type: String // bracket
) : Parcelable