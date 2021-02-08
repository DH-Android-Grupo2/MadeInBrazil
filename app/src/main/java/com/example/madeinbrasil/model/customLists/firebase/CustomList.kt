package com.example.madeinbrasil.model.customLists.firebase

data class CustomList(
    val name: String,
    val description: String?,
    val movies: List<String>?,
    val series: List<String>?,
    var id: String = "",
    var userId: String = "",
    var ownerName: String = ""
) {
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
