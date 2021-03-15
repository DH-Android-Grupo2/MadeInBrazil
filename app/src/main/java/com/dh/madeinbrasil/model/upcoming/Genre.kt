package com.dh.madeinbrasil.model.upcoming

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Genre (
@PrimaryKey
      val genreId: Int,
      val movieId: Int
        )