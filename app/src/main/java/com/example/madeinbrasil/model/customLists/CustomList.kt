package com.example.madeinbrasil.model.customLists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_lists")
data class CustomList(
    @PrimaryKey(autoGenerate = true)
    val listId: Long,
    var name: String,
    var description: String?,
    @ColumnInfo(name = "user_owner_id")
    val userOwnerId: Int
)