package com.example.madeinbrasil.model.gender

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenderSerie(
    val genres: List<Genre>
): Parcelable