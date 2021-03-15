package com.dh.madeinbrasil.model.people

import android.os.Parcelable
import com.dh.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviesTVFromPerson(
        val cast: List<Result>?,
        val id: Int
): Parcelable