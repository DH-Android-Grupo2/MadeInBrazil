package com.example.madeinbrasil.model.people


import android.os.Parcelable
import com.example.madeinbrasil.model.movieCredits.Crew
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvPerson (
        val cast: List<ResultSearch>?,
        val id: Int
): Parcelable

