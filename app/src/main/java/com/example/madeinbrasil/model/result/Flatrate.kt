package com.example.madeinbrasil.model.result

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flatrate(
    val display_priority: Int,
    val logo_path: String,
    val provider_id: Int,
    val provider_name: String
): Parcelable