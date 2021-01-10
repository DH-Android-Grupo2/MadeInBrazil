package com.example.madeinbrasil.model.result

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BR(
    val flatrate: List<Flatrate>?,
    val link: String
): Parcelable