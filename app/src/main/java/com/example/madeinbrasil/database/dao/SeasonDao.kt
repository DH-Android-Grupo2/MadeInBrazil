package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.database.entities.season.EpisodeEntity
import com.example.madeinbrasil.database.entities.season.SeasonEntity

@Dao
interface SeasonDao {
    @Query("SELECT * FROM SeasonEntity WHERE serieId = :id ORDER BY season_number ASC")
    suspend fun getSeasonsById(id: Int): List<SeasonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeason(seasonEntity: SeasonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episodeEntity: EpisodeEntity)
}