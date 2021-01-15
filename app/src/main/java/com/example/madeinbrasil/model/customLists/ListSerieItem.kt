package com.example.madeinbrasil.model.customLists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_serie_items")
data class ListSerieItem(
        @PrimaryKey
        val serieId: Long,
        var title: String?,
        @ColumnInfo(name = "backdrop_path")
        var backdropPath: String?,
        @ColumnInfo(name = "original_title")
        val originalTitle: String?,
)