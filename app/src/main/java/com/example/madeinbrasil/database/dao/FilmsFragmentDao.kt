package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.search.movie.SearchResult
import com.example.madeinbrasil.model.upcoming.Result


@Dao
interface FilmsFragmentDao {
    @Query("SELECT * FROM movies_home")
    suspend fun getResults(): List<Result>

    @Query("SELECT * FROM home_discover WHERE type == 1")
    suspend fun getSearchResult(): List<ResultSearch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovies(movies: List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(movies: List<SearchResult>)


   //@Query("SELECT * FROM movies_home WHERE type == 0")
   //suspend fun getSearchMovies() : List<Result>



}