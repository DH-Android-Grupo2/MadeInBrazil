package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity(tableName = "serie_detailed")
data class SerieDetailed(
//        @PrimaryKey
//        @ColumnInfo(name = "id_serie_detailed")
        val id: Int,
//        @ColumnInfo(name = "backdrop_path")
        @SerializedName("backdrop_path")
        var backdropPath: String?,
//        @Ignore
        var credits: Credits?,
//        @ColumnInfo(name = "episode_run_time")
        @SerializedName("episode_run_time")
//        @TypeConverters(GenreConverter::class)
        var episodeRunTime: List<Int>?,
//        @ColumnInfo(name = "first_air_date")
        @SerializedName("first_air_date")
        var firstAirDate: String?,
//        @Ignore
        val genres: List<Genre>?,
        val homepage: String?,
        val name: String?,
        var overview: String?,
//        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?,
//        @Ignore
        val recommendations: Recommendations?,
//        @Ignore
        val seasons: List<Season>?,
//        @Ignore
        val similar: Similar?,
//        @Ignore
        val videos: Videos?,
//        @ColumnInfo(name = "vote_average")
        @SerializedName("vote_average")
        val voteAverage: Double?,
//        @Ignore
        @SerializedName("watch/providers")
        val watchProviders: Watchproviders?
): Parcelable