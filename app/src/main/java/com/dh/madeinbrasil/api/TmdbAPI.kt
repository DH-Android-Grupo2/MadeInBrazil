package com.dh.madeinbrasil.api

import com.dh.madeinbrasil.model.discover.DiscoverMovie
import com.dh.madeinbrasil.model.discoverTV.DiscoverTV
import com.dh.madeinbrasil.model.gender.GenderMovie
import com.dh.madeinbrasil.model.gender.GenderSerie
import com.dh.madeinbrasil.model.movieCredits.MovieCredits
import com.dh.madeinbrasil.model.nowPlaying.NowPlaying
import com.dh.madeinbrasil.model.people.Person
import com.dh.madeinbrasil.model.result.MovieDetailed
import com.dh.madeinbrasil.model.search.movie.SearchMovie
import com.dh.madeinbrasil.model.search.serie.SearchSeries
import com.dh.madeinbrasil.model.seasons.Seasons
import com.dh.madeinbrasil.model.serieDetailed.SerieDetailed
import com.dh.madeinbrasil.model.upcoming.Upcoming
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
    ): Response<MovieDetailed>

    @GET("person/{person_id}")
    suspend fun personDetails(
            @Path("person_id") personId: Int
    ): Response<Person>

    @GET("tv/{tv_id}")
    suspend fun serieDetails(
        @Path("tv_id") serieId: Int
    ): Response<SerieDetailed>

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
        @Path("movie_id") movieId: Int
    ): Response<MovieCredits>

    @GET("movie/now_playing")
    suspend fun movieNowPlaying(
        @Query("page") pageNumber: Int
    ): Response<NowPlaying>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") pageNumber: Int,
        @Query("query") query: String
    ): Response<SearchMovie>

    @GET("search/tv")
    suspend fun searchSerie(
        @Query("page") pageNumber: Int,
        @Query("query") query: String
    ): Response<SearchSeries>

    @GET("genre/movie/list")
    suspend fun movieGenres(): Response<GenderMovie>

    @GET("discover/movie")
   suspend fun discoverMovies(
            @Query("page") pageNumber: Int,
           @Query("with_genres") genre: String?,
            @Query("with_original_language")  language: String = "pt"
   ) : Response<DiscoverMovie>

   @GET ("discover/tv")
   suspend fun discoverTV(
           @Query("page") pageNumber: Int,
           @Query("with_genres")  genre: String?,
           @Query("with_original_language") language: String = "pt"
                    ) : Response<DiscoverTV>


    @GET("genre/tv/list")
    suspend fun serieGenders(): Response<GenderSerie>

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun seasonsEpisodes(
        @Path("tv_id") serieId: Int,
        @Path("season_number") seasonNumber: Int
    ): Response<Seasons>
}
