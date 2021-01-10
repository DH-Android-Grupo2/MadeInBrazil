package com.example.madeinbrasil.model.people

import android.os.Parcelable
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.example.madeinbrasil.model.serieDetailed.Credits
import com.example.madeinbrasil.model.serieDetailed.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
        val adult: Boolean?,
        var biography: String?,
        var birthday: String?,
        val deathday: String?,
        val gender: Int?,
        val homepage: String?,
        val id: Int,
        val imdb_id: String?,
        val known_for_department: String?,
        val movie_credits: MoviesTVFromPerson?,
        val name: String?,
        val place_of_birth: String?,
        val popularity: Double?,
        var profile_path: String?,
        val tv_credits: TvPerson?
): Parcelable