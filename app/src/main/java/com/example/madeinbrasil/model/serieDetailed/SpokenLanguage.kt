package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
): Parcelable