package com.dh.madeinbrasil.model.gender

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GenreSelected(var Selected : MutableList<String> )
 : Parcelable