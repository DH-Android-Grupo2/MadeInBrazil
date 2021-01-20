package com.example.madeinbrasil.database.dao

import androidx.room.*
import com.example.madeinbrasil.database.entities.recommendations.RecommendationEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef

@Dao
interface RecommendationDao {
    @Query("SELECT * FROM RecommendationMidiaCrossRef WHERE id_midia = :id")
    suspend fun getRecommendations(id: Int): List<RecommendationMidiaCrossRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(recommendation: RecommendationMidiaCrossRef)
}