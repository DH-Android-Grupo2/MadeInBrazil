package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Credits(
        @Relation(parentColumn = "serie_id", entityColumn = "id")
        val cast: List<Cast>?,
        @Relation(parentColumn = "serie_id", entityColumn = "id")
        val crew: List<Crew>?
): Parcelable