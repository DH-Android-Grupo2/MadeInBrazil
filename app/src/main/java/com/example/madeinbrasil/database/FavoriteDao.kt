package com.example.madeinbrasil.database

import androidx.room.*
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.database.entities.favorites.SerieDetailedWithGenres
import com.example.madeinbrasil.model.serieDetailed.Genre

@Dao
interface FavoriteDao {
//    @Query("SELECT * FROM favorites")
//    suspend fun getAllFavorites(): List<Favorites>

    @Query("SELECT * FROM favorites_serie_detailed")
    suspend fun getSerieFavorites(): List<FavoritesSerieDetailed>

    @Query("SELECT * FROM favorites_movie_detailed")
    suspend fun getMovieFavorites(): List<FavoritesMovieDetailed>

//    @Query("SELECT * FROM favorites_movie_detailed, favorites_serie_detailed")
//    suspend fun getMovieAndSerieFavorite(): List<FavoritesSerieDetailed>

    @Transaction
    @Query("SELECT * FROM favorites_serie_detailed")
    suspend fun getSerieDetailedWithGenre(): List<SerieDetailedWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: FavoritesSerieDetailed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: List<Genre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoritesMovieDetailed)

    @Delete
    suspend fun deleteMovie(movie: FavoritesMovieDetailed)

    @Delete
    suspend fun deleteSerie(serie:FavoritesSerieDetailed)

}