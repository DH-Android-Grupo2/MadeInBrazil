package com.example.madeinbrasil.view.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Item(
    val nome: String, val imagem: String
) : Parcelable