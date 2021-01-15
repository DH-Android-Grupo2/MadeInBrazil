package com.example.madeinbrasil.database.entities.favorites

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.example.madeinbrasil.model.result.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites_movie_detailed")
data class FavoritesMovieDetailed (
        @PrimaryKey
        @ColumnInfo(name = "favorite_movie_id")
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
        val checked: Boolean = false,
        val idMovie: Int = 1
): Parcelable