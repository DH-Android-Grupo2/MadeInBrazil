package com.example.madeinbrasil.model.customLists.firebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val id: String,
    var backdropPath: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    var overview: String?,
    val popularity: Double?,
    var posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    var firstAirDate: String?,
    var name: String?,
):Parcelable {
    constructor(): this(
            "",
            "",
            "",
            "",
            "",
            0.0,
            "",
            "",
            "",
            0.0,
            0,
            "",
            ""
    )
}
