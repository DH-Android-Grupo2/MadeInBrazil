package com.example.madeinbrasil.view.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lista (val nome: String,
                  val criador: String,
                  val imagem: String,
                  val itens: MutableList<Item> = mutableListOf<Item>()
) : Parcelable