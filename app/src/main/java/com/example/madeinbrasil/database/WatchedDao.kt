package com.example.madeinbrasil.database

import androidx.room.*
import com.example.madeinbrasil.database.entities.watched.WatchedMovieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedSerieDetailed

@Dao
interface WatchedDao {
    @Query("SELECT * FROM watched_movie_detailed")
    suspend fun getMovieWatched(): List<WatchedMovieDetailed>

    @Query("SELECT * FROM watched_serie_detailed")
    suspend fun getSerieWatched(): List<WatchedSerieDetailed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWatched(movie: WatchedMovieDetailed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerieWatched(serie: WatchedSerieDetailed)

    @Delete
    suspend fun deleteWatchedMovie(movie: WatchedMovieDetailed)

    @Delete
    suspend fun deleteWatchedSerie(serie: WatchedSerieDetailed)
}
