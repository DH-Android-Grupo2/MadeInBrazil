package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class Watchproviders(
//        @PrimaryKey(autoGenerate = true)
//        val watchProvidersId: Int,
        val results: Results?
): Parcelable