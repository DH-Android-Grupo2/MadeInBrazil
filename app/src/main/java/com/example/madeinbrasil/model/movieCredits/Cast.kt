package com.example.madeinbrasil.model.movieCredits

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
        val adult: Boolean,
        val cast_id: Int,
        val character: String,
        val credit_id: String,
        val gender: Int,
        val id: Int,
        val known_for_department: String,
        val name: String,
        val order: Int,
        val original_name: String,
        val popularity: Double,
        var profile_path: String?,
        val backdrop_path: String?,
        val genre_ids: List<Int>?,
        val original_language: String?,
        val original_title: String?,
        val overview: String?,
        var poster_path: String?,
        val release_date: String?,
        val title: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?
):Parcelable