package com.example.madeinbrasil.database.entities.genre

import android.os.Parcelable
import androidx.room.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "genre")
data class GenreEntity (
        @PrimaryKey
        @ColumnInfo(name = "id_genre")
        val id: Int,
        val name: String?,
        @TypeConverters(GenreConverter::class)
        val serieId: List<Int>
): Parcelable