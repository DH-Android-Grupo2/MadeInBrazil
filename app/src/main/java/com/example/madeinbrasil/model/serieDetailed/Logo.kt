package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logo(
    val aspect_ratio: Int,
    val path: String
): Parcelable