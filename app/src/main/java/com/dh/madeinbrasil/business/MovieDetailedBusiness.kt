package com.dh.madeinbrasil.business

import android.content.Context
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.database.entities.cast.CastFirebase
import com.dh.madeinbrasil.database.entities.genre.GenreFirebase
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.result.MovieDetailed
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.repository.MovieDetailedRepository
import com.google.firebase.firestore.DocumentSnapshot

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

    suspend fun getMidiaFireBase(id: Int): DocumentSnapshot? {
        repository.getMidiaFireBase(id)?.let {
            return it
        }?: run {
            return null
        }
    }

    suspend fun getCast(id: Int): DocumentSnapshot? {
        repository.getCast(id)?.let {
            return it
        }?: run {
            return null
        }
    }

    suspend fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        repository.setMidiaFireBase(id, infos)
    }

    suspend fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        repository.setGenreFireBase(id, infos)
    }

    suspend fun setCastFireBase(id: Int, infos: CastFirebase) {
        repository.setCastFireBase(id, infos)
    }

    suspend fun updateUser(user: User) {
        repository.updateUser(user)
    }

    suspend fun getSearchDB(): List<Result> {
        return repository.getSearchDB()
    }
}