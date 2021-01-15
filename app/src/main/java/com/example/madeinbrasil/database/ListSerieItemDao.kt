package com.example.madeinbrasil.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.madeinbrasil.model.customLists.ListSerieItem

@Dao
interface ListSerieItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeries(series: List<ListSerieItem>)
}