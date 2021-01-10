package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Network(
    val id: Int?,
    val logo_path: String?,
    val name: String?,
    val origin_country: String?
): Parcelable