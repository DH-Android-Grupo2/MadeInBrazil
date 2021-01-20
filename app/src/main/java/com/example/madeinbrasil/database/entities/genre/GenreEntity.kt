package com.example.madeinbrasil.database.entities.genre

import android.os.Parcelable
import androidx.room.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "genre_entity")
data class GenreEntity (
        @ColumnInfo(name = "id_genre")
        @TypeConverters(GenreConverter::class)
        val id: List<Int>,
        @TypeConverters(GenreConverter::class)
        val name: List<String>?,
        @PrimaryKey
        val midiaId: Int
): Parcelable