package com.example.madeinbrasil.model.customLists.firebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomList(
    val name: String,
    val description: String?,
    val movies: List<String>?,
    val series: List<String>?,
    var id: String = "",
    var userId: String = "",
    var ownerName: String = ""
): Parcelable {
    constructor(): this(
            "",
            "",
            listOf(),
            listOf(),
            "",
            "",
            ""
    )
}
