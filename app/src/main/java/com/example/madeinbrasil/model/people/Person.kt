package com.example.madeinbrasil.model.people

import android.os.Parcelable
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    val adult: Boolean?,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val gender: Int?,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val known_for_department: String?,
    val movie_credits: MovieCredits?,
    val name: String?,
    val place_of_birth: String?,
    val popularity: Double?,
    var profile_path: String?,
    val tv_credits: MovieCredits?
): Parcelable