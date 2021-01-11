package com.example.madeinbrasil.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie:MovieDetailed, idMovie: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie:SerieDetailed, idSerie: Int)

    @Delete
    suspend fun deleteMovie(movie: MovieDetailed)

    @Delete
    suspend fun deleteSerie(serie:SerieDetailed)
}