package com.dh.madeinbrasil.repository

import com.dh.madeinbrasil.api.APIService
import com.dh.madeinbrasil.api.ResponseAPI
import java.lang.Exception

class HomeRepository {

    suspend fun getUpcoming(pageNumber: Int): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.upcoming(pageNumber)

            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }

    suspend fun getMovieDetails(movieId: Int): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.movieDetails(movieId)

            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }

    suspend fun getNowPlaying(pageNumber: Int): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.movieNowPlaying(pageNumber)

            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }


    suspend fun getDiscover(pageNumber: Int, genre: String?): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.discoverMovies(pageNumber,genre)
            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }


    suspend fun  getDiscoverTV(pageNumber: Int, genre: String?): ResponseAPI{
        return try {
            val response = APIService.tmdbApiDiscoverTv.discoverTV(pageNumber, genre)
            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }

}