package com.example.madeinbrasil.model.people

import android.os.Parcelable
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.movieCredits.Crew
import com.example.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviesTVFromPerson(
        val cast: List<Result>?,
        val crew: List<Crew>?,
        val id: Int
): Parcelable