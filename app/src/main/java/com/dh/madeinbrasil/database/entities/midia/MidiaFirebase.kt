package com.dh.madeinbrasil.database.entities.midia

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MidiaFirebase(
        var id: Int = 0,
        var backdropPath: String? = "",
        var cast: List<Int>? = listOf(),
        var episodeRunTime: String? = "",
        var firstAirDate: String? = "",
        var genres: List<Int>? = listOf(),
        var homepage: String? = "",
        var name: String? = "",
        var overview: String? = "",
        var posterPath: String? = "",
        var recommendations: List<Int>? = listOf(),
        var seasons: List<Int>? = listOf(),
        var similar: List<Int>? = listOf(),
        var voteAverage: Double? = 0.0,
        var origin: Int = 0,
        var midiaType: Int = 0
): Parcelable