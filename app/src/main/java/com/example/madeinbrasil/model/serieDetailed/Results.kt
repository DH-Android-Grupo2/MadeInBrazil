package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class Results(
//        @PrimaryKey(autoGenerate = true)
//        val resultId: Int,
        val BR: BR?,
): Parcelable