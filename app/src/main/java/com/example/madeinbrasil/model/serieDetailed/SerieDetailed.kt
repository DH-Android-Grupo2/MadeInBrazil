package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "series_detailed")
data class SerieDetailed(
        @PrimaryKey
        @ColumnInfo(name = "serie_id")
        val id: Int,
        @ColumnInfo(name = "backdrop_path")
        @SerializedName("backdrop_path")
        var backdropPath: String?,
        @Embedded
        @Relation(parentColumn = "serie_id", entityColumn = "id")
        val credits: Credits?,
        @ColumnInfo(name = "episode_run_time")
        @SerializedName("episode_run_time")
        var episodeRunTime: List<Int>?,
        @ColumnInfo(name = "first_air_date")
        @SerializedName("first_air_date")
        val firstAirDate: String?,
        val genres: List<Genre>?,
        val homepage: String?,
        val name: String?,
        var overview: String?,
        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?,
        val recommendations: Recommendations?,
        val seasons: List<Season>?,
        val similar: Similar?,
        val videos: Videos?,
        @ColumnInfo(name = "vote_average")
        @SerializedName("vote_average")
        val voteAverage: Double?,
        @ColumnInfo(name = "watch/providers")
        @SerializedName("watch/providers")
        val watchProviders: Watchproviders?
): Parcelable