package com.example.madeinbrasil.model.favorites

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites")
data class Favorites(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val movies: MovieDetailed,
        val series: SerieDetailed
): Parcelable