package com.example.madeinbrasil.model.classe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Films (var img: String,
            var name: String,
            var description: String,
            var gender: String,
            var year: String,
            var time: String): Parcelable