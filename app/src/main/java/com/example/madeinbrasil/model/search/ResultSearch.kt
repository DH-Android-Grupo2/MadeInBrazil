package com.example.madeinbrasil.model.search

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "home_discover")
data class ResultSearch(
//        val serieDetailedId: Int,
//        val recommendationId: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    @ColumnInfo(name ="backdrop_path")
    var backdropPath: String?,
    @SerializedName("first_air_date")
    @ColumnInfo(name ="first_air_date")
    val firstAirDate: String,
    @ColumnInfo(name ="genre_ids")
    @SerializedName("genre_ids")
    @TypeConverters(GenreConverter::class)
    val genreIds: List<Int>,
    @PrimaryKey
    val id: Int,
    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String?,
    val name: String,
    @ColumnInfo(name = "origin_country")
    @SerializedName("origin_country")
    @TypeConverters(GenreConverter::class)
    val originCountry: List<String>,
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    val originalName: String?,
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String?,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    val voteCount: Int
): Parcelable {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<ResultSearch> = object : DiffUtil.ItemCallback<ResultSearch>() {
            override fun areItemsTheSame(oldItem: ResultSearch, newItem: ResultSearch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultSearch, newItem: ResultSearch): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
