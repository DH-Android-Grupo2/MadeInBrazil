package com.example.madeinbrasil.database.entities.recommendations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName

@Entity
data class RecommendationEntity (
        @PrimaryKey
        @ColumnInfo(name = "midia_id")
        val midiaid: Int,
//        @SerializedName("media_type")
//        @ColumnInfo(name = "media_type")
//        val midiaType: Int?,
//        val name: String,
//        @ColumnInfo(name = "poster_path")
//        @SerializedName("poster_path")
//        var posterPath: String?
)