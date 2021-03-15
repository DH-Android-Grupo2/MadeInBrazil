package com.dh.madeinbrasil.model.customLists.firebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomList(
    var name: String,
    var description: String?,
    var movies: List<String>,
    var series: List<String>,
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
