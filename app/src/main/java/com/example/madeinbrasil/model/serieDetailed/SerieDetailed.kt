package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SerieDetailed(
        val id: Int,
        @SerializedName("backdrop_path")
        var backdropPath: String?,
        var credits: Credits?,
        @SerializedName("episode_run_time")
        var episodeRunTime: List<Int>?,
        @SerializedName("first_air_date")
        var firstAirDate: String?,
        val genres: List<Genre>?,
        val homepage: String?,
        val name: String?,
        var overview: String?,
        @SerializedName("poster_path")
        var posterPath: String?,
        val recommendations: Recommendations?,
        val seasons: List<Season>?,
        val similar: Similar?,
        val videos: Videos?,
        @SerializedName("vote_average")
        val voteAverage: Double?,
        @SerializedName("watch/providers")
        val watchProviders: Watchproviders?
): Parcelable