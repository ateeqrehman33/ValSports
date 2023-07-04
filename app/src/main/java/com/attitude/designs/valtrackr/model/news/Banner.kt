package com.attitude.designs.valtrackr.model.news


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    @SerializedName("content_type")
    val contentType: String, // image/jpeg
    @SerializedName("created_at")
    val createdAt: String, // 2023-03-27T10:28:44.989Z
    @SerializedName("created_by")
    val createdBy: String, // blt14ffa27ee1cfff8f
    @SerializedName("file_size")
    val fileSize: String, // 3207420
    @SerializedName("filename")
    val filename: String, // VCT_SPONSORS_cougar.jpg
    @SerializedName("is_dir")
    val isDir: Boolean, // false
    @SerializedName("parent_uid")
    val parentUid: String?, // bltbf4c4aa094c2e327
    @SerializedName("publish_details")
    val publishDetails: PublishDetails,
    @SerializedName("title")
    val title: String, // VCT_SPONSORS_cougar.jpg
    @SerializedName("uid")
    val uid: String, // bltd5b5e234286a5a4e
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-03-27T10:28:44.989Z
    @SerializedName("updated_by")
    val updatedBy: String, // blt14ffa27ee1cfff8f
    @SerializedName("url")
    val url: String, // https://images.contentstack.io/v3/assets/bltb730eada072bdbf4/bltd5b5e234286a5a4e/64216fdcc8e64e0404191bb9/VCT_SPONSORS_cougar.jpg
    @SerializedName("_version")
    val version: Int // 1
) : Parcelable