package com.example.madeinbrasil.api

import com.example.madeinbrasil.model.gender.GenderMovie
import com.example.madeinbrasil.model.nowPlaying.NowPlaying
import com.example.madeinbrasil.model.search.movie.SearchMovie
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.upcoming.Upcoming
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {

    @GET("movie/upcoming")
    suspend fun upcoming(
        @Query("page") pageNumber: Int
    ): Response<Upcoming>

    @GET("movie/{movie_id}")
    suspend fun movieDetails(
        @Path("movie_id") movieId: Int
    ): Response<Any>

    @GET("movie/now_playing")
    suspend fun movieNowPlaying(
        @Query("page") pageNumber: Int
    ): Response<NowPlaying>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") pageNumber: Int,
        @Query("query") query: String
    ): Response<SearchMovie>

    @GET("genre/movie/list")
    suspend fun movieGenres(): Response<GenderMovie>

}
