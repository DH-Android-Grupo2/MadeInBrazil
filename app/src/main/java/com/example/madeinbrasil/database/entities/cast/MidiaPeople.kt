package com.example.madeinbrasil.database.entities.cast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MidiaPeople (
        @PrimaryKey
        val midiaId: Int,
        val title: String?,
        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?,
        var midiaType: Int
)