package com.example.madeinbrasil.database.entities.comments

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Production (
        @PrimaryKey
        val productionId: Int
)