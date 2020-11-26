package com.example.madeinbrasil.model.`class`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Series (var img: String,
              var name: String,
              var description: String,
              var gender: String,
              var year: String,
              var time: String): Parcelable