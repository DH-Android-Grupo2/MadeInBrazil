package com.example.madeinbrasil.database

import androidx.room.*
import com.example.madeinbrasil.model.favorites.Favorites
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.serieDetailed.Cast
import com.example.madeinbrasil.model.serieDetailed.Crew
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed

@Dao
interface FavoriteDao {
//    @Query("SELECT * FROM favorites")
//    suspend fun getAllFavorites(): List<Favorites>

    @Query("SELECT * FROM series_detailed")
    suspend fun getSerieFavorites(): List<SerieDetailed>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovie(movie:MovieDetailed, idMovie: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie:SerieDetailed)

//    @Delete
//    suspend fun deleteMovie(movie: MovieDetailed)

    @Delete
    suspend fun deleteSerie(serie:SerieDetailed)
}