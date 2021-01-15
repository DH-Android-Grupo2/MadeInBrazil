package com.example.madeinbrasil.database.entities.favorites

import android.os.Parcelable
import androidx.room.*
import com.example.madeinbrasil.model.serieDetailed.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites_serie_detailed")
data class FavoritesSerieDetailed(
        @PrimaryKey
        @ColumnInfo(name= "favorites_serie_id")
        var id: Int,
        @ColumnInfo(name = "backdrop_path")
        @SerializedName("backdrop_path")
        var backdropPath: String?,
        @ColumnInfo(name = "episode_run_time")
        @SerializedName("episode_run_time")
        @TypeConverters(GenreConverter::class)
        var episodeRunTime: List<Int>?,
        @ColumnInfo(name = "first_air_date")
        @SerializedName("first_air_date")
        var firstAirDate: String?,
        var homepage: String?,
        var name: String?,
        var overview: String?,
        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?,
        @ColumnInfo(name = "vote_average")
        @SerializedName("vote_average")
        var voteAverage: Double?,
        var checked: Boolean = false,
        var idSerie: Int = 2
): Parcelable