package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.database.entities.cast.MidiaPeople
import com.example.madeinbrasil.database.entities.cast.MidiaCastCrossRef

@Dao
interface PeopleDao {
    @Query("SELECT * FROM MidiaCastCrossRef WHERE id_midia = :id ORDER BY `order` ASC")
    suspend fun getPeopleWithMidia(id: Int): List<MidiaCastCrossRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(people: MidiaCastCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMidias(midia: MidiaPeople)
}