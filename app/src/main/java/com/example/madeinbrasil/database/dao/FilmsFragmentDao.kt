package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.model.search.movie.SearchResult
import com.example.madeinbrasil.model.upcoming.Result


@Dao
interface FilmsFragmentDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovies(movies: List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(movies: List<SearchResult>)


   //@Query("SELECT * FROM movies_home WHERE type == 0")
   //suspend fun getSearchMovies() : List<Result>



}