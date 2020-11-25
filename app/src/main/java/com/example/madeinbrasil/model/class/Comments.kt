package com.example.madeinbrasil.model.`class`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Comments(
    val name: String,
    val img: String,
    val comment: String
): Parcelable