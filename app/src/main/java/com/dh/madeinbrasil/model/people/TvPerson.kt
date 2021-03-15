package com.dh.madeinbrasil.model.people


import android.os.Parcelable
import com.dh.madeinbrasil.model.search.ResultSearch
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvPerson (
        val cast: List<ResultSearch>?,
        val id: Int
): Parcelable

