package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.database.entities.genre.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT name FROM genre_entity WHERE midiaId = :id")
    suspend fun getGenreById(id: Int): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genreEntity: GenreEntity)
}