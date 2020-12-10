package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flatrate(
    val display_priority: Int?,
    @SerializedName("logo_path")
    var logoPath: String?,
    val provider_id: Int?,
    val provider_name: String?
): Parcelable