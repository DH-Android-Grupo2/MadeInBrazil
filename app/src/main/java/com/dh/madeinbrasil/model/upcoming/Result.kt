package com.dh.madeinbrasil.model.upcoming

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies_home", primaryKeys = ["id", "type"])
 class Result (
        val adult: Boolean?,
        @SerializedName("backdrop_path")
        @ColumnInfo(name ="backdrop_path")
        var backdropPath: String?,
        @ColumnInfo(name ="genre_ids")
        @SerializedName("genre_ids")
        @TypeConverters(GenreConverter::class)
        val genreIds: MutableList<Int>,
        var id: Int,
        @ColumnInfo(name ="first_air_date")
        @SerializedName("first_air_date")
        var firstAirDate: String?,
        @ColumnInfo(name ="origin_country")
        @SerializedName("origin_country")
        @TypeConverters(GenreConverter::class)
        var originalCountry: MutableList<String>?,
        @SerializedName("original_language")
        @ColumnInfo(name ="original_language")
        val originalLanguage: String?,
        @SerializedName("original_title")
        @ColumnInfo(name = "original_title")
        val originalTitle: String?,
        val overview: String?,
        val popularity: Double?,
        @SerializedName("poster_path")
        @ColumnInfo(name = "poster_path")
        var posterPath: String?,
        @ColumnInfo(name ="release_date")
        @SerializedName("release_date")
        val releaseDate: String?,
        var title: String?,
        var name: String?,
        @ColumnInfo(name ="original_name")
        @SerializedName("original_name")
        var originalName: String?,
        val video: Boolean?,
        @ColumnInfo(name ="vote_average")
        @SerializedName("vote_average")
        val voteAverage: Float?,
        @ColumnInfo(name ="vote_count")
        @SerializedName("vote_count")
        val voteCount: Int?,
        var type: Int

        //Now playing =1
        //upcoming = 2
        //discover = 3
        //searchMovie = 0


        ) : Parcelable {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Result> = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}