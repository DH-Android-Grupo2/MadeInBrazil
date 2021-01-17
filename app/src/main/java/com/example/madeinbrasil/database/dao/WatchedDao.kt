package com.example.madeinbrasil.database.dao

import androidx.room.*
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.watched.MidiaWithWatched
import com.example.madeinbrasil.database.entities.watched.Watched

@Dao
interface WatchedDao {
    @Query("SELECT * FROM midia")
    suspend fun getMidiaWatched(): List<MidiaEntity>

    @Query("DELETE FROM Watched WHERE midiaId = :id")
    suspend fun deleteByIdWatched(id: Int)

    @Transaction
    @Query("SELECT * FROM midia, watched WHERE id_midia = id_watched")
    suspend fun getMidiaWithWatched(): List<MidiaWithWatched>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatched(watched: Watched)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMidia(media: MidiaEntity)
}
