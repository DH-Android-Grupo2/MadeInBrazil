package com.example.madeinbrasil.model.result

import android.os.Parcelable
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailed(
        val adult: Boolean?,
        var backdrop_path: String?,
        val budget: Int?,
        val credits: MovieCredits,
        val genres: List<Genre>?,
        val homepage: String?,
        val id: Int,
        val imdb_id: String?,
        val original_language: String?,
        val original_title: String?,
        var overview: String?,
        val popularity: Double?,
        var poster_path: String?,
        val release_date: String?,
        val revenue: Int?,
        val runtime: Int?,
        val status: String?,
        val tagline: String?,
        val title: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?,
        val recommendations: Recommendations?,
        val similar: Similar?,
        val videos: Videos?,
        @SerializedName("watch/providers")
        val watch_providers: Watchproviders?
): Parcelable