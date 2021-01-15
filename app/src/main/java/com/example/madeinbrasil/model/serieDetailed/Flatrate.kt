package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class Flatrate(
//        val brIdFlatrate: Int,
    val display_priority: Int?,
    @SerializedName("logo_path")
    var logoPath: String?,
//    @PrimaryKey
    val provider_id: Int?,
    val provider_name: String?
): Parcelable