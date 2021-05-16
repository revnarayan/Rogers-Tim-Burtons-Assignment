package com.revnarayan.rogerstimburtonsassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("size") val size: String?,
    @SerializedName("cost") val cost: Double?,
    @SerializedName("type") val type: String?
): Parcelable

