package com.example.madeinbrasil.model.classe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Comments(
    val name: String,
    val img: String,
    val comment: String
): Parcelable