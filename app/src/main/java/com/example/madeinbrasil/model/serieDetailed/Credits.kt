package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class Credits(
//        @PrimaryKey(autoGenerate = true)
//        val idCredit: Int,
        val cast: List<Cast>?
//        val crew: List<Crew>?
): Parcelable