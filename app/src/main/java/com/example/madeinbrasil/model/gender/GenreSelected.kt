package com.example.madeinbrasil.model.gender

import android.os.Parcelable
import com.example.madeinbrasil.model.result.Genre
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GenreSelected(var Selected : MutableList<Genre> )
 : Parcelable