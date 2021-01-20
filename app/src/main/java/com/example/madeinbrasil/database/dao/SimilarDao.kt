package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef

@Dao
interface SimilarDao {
    @Query("SELECT * FROM SimilarMidiaCrossRef WHERE id_midia = :id")
    suspend fun getSimilar(id: Int): List<SimilarMidiaCrossRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilar(similar: SimilarMidiaCrossRef)
}