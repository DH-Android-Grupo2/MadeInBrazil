package com.example.madeinbrasil.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.model.search.ResultSearch


@Dao
interface DiscoverTvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscover(tv: List<ResultSearch>)


    @Query("SELECT * FROM home_discover WHERE type == 0")
    suspend fun getDiscover() : List<ResultSearch>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchTV(tv: List<ResultSearch>)


    @Query("SELECT * FROM home_discover WHERE type == 1")
    suspend fun getSearchTV() : List<ResultSearch>


}