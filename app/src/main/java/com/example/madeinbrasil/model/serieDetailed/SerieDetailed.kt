package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SerieDetailed(
        var backdrop_path: String?,
        val created_by: List<CreatedBy>?,
        val credits: Credits?,
        val episode_run_time: List<Int>?,
        val first_air_date: String?,
        val genres: List<Genre>?,
        val homepage: String?,
        val id: Int,
        val in_production: Boolean?,
        val languages: List<String>?,
        val last_air_date: String?,
        val last_episode_to_air: LastEpisodeToAir?,
        val name: String?,
        val networks: List<Network>?,
        val next_episode_to_air: String?,
        val number_of_episodes: Int?,
        val number_of_seasons: Int?,
        val origin_country: List<String>?,
        val original_language: String?,
        val original_name: String?,
        var overview: String?,
        val popularity: Double?,
        var poster_path: String?,
        val production_companies: List<ProductionCompany>?,
        val production_countries: List<ProductionCountry>?,
        val recommendations: Recommendations?,
        val seasons: List<Season>?,
        val similar: Similar?,
        val spoken_languages: List<SpokenLanguage>?,
        val status: String?,
        val tagline: String?,
        val type: String?,
        val videos: Videos?,
        val vote_average: Double?,
        val vote_count: Int?,
        @SerializedName("watch/providers")
    val watch_providers: Watchproviders?
): Parcelable