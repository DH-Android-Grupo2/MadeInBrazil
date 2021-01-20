package com.example.madeinbrasil.database.dao

import androidx.room.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.example.madeinbrasil.model.upcoming.Result



@Dao
interface UpcomingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(movies: List<Result>)


    @Query("SELECT * FROM movies_home WHERE type == 2 ")
    suspend fun getUpcoming(): List<Result>

    @Query("SELECT * FROM movies_home WHERE type == 1")
    suspend fun getNowPlaying(): List<Result>


    @Query("SELECT * FROM movies_home WHERE type == 3")
    suspend fun getDiscover(): List<Result>

}