package com.example.madeinbrasil.model.trailer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trailer(
    val id: Int,
    val result: List<ResultTrailer>
): Parcelable