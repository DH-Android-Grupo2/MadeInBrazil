package com.example.madeinbrasil.database.entities.midia

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "midia")
data class MidiaEntity (
        @PrimaryKey
        @ColumnInfo(name = "id_midia")
        val id: Int,
        @ColumnInfo(name = "backdrop_path")
        @SerializedName("backdrop_path")
        var backdropPath: String?,
        val homepage: String?,
        @ColumnInfo(name = "original_language")
        @SerializedName("original_language")
        val originalLanguage: String?,
        @ColumnInfo(name = "original_title")
        @SerializedName("original_title")
        val originalTitle: String?,
        var overview: String?,
        val popularity: Double?,
        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?,
        @ColumnInfo(name = "release_date")
        @SerializedName("release_date")
        val releaseDate: String?,
        val runtime: Int?,
        val title: String?,
        @ColumnInfo(name = "vote_average")
        @SerializedName("vote_average")
        val voteAverage: Double?,
        @ColumnInfo(name = "vote_count")
        @SerializedName("vote_count")
        val voteCount: Int?,
        @ColumnInfo(name = "episode_run_time")
        @SerializedName("episode_run_time")
        @TypeConverters(GenreConverter::class)
        var episodeRunTime: List<Int>?,
        @ColumnInfo(name = "first_air_date")
        @SerializedName("first_air_date")
        var firstAirDate: String?,
        var name: String?,
        val midiaType: Int
): Parcelable