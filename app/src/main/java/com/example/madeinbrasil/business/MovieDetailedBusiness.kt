package com.example.madeinbrasil.business

import android.content.Context
import android.util.Log
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedMovieDetailed
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.repository.MovieDetailedRepository

class MovieDetailedBusiness(val context: Context)  {

    private val repository:MovieDetailedRepository by lazy {
        MovieDetailedRepository(context)
    }

    suspend fun getMovie(movieId: Int): ResponseAPI {
        val response = repository.getMovie(movieId)
        return if (response is ResponseAPI.Success) {
            val movie = response.data as MovieDetailed
            if(movie.overview == "") {
                movie.overview = "Sinopse não encontrada"
            }
            movie.poster_path = movie.poster_path?.getFullImagePath()
            movie.backdrop_path?.let { string ->
                movie.backdrop_path = string.getFullImagePath()
            }?: run {
                movie.backdrop_path = movie.poster_path
            }
            movie?.credits?.cast?.forEach {
                it.profilePath = it.profilePath?.getFullImagePath()
            }
            movie.recommendations?.results = movie.recommendations?.results?.filter { it.originalLanguage.equals("pt") }
            movie.recommendations?.results?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
            }
            movie.similar?.results = movie.similar?.results?.filter { it.originalLanguage.equals("pt") }
            movie?.similar?.results?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
            }
            movie.watch_providers?.results?.BR?.flatrate?.forEach {
                it.logo_path = it.logo_path?.getFullImagePath()
            }
            ResponseAPI.Success(movie)
        } else {
            response
        }
    }

    suspend fun insertMovieFavorite(movie: FavoritesMovieDetailed) {
        repository.insertMovieFavorite(movie)
    }

    suspend fun deleteMovieFavorite(movie: FavoritesMovieDetailed) {
        repository.deleteMovieFavorite(movie)
    }

    suspend fun insertMovieWatched(movie: WatchedMovieDetailed) {
        repository.insertMovieWatched(movie)
    }

    suspend fun deleteMoviewatched(movie: WatchedMovieDetailed) {
        repository.deleteMoviewatched(movie)
    }
}