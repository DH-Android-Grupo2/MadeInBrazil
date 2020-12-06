package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkX(
    val id: Int,
    val logo: Logo,
    val name: String,
    val origin_country: String
): Parcelable