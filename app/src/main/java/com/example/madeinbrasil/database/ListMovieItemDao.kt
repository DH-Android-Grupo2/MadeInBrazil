package com.example.madeinbrasil.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.madeinbrasil.model.customLists.ListMovieItem

@Dao
interface ListMovieItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<ListMovieItem>)

}