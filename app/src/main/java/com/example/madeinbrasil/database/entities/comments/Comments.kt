package com.example.madeinbrasil.database.entities.comments

import androidx.room.PrimaryKey

data class Comments (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val text: String,
        val userId: Int,
        val productionId: Int
)