package com.example.madeinbrasil.database.entities

data class User (
    val email: String = "",
    val name: String = "",
    val favorites: MutableList<Int> = mutableListOf(),
    val watched: MutableList<Int> = mutableListOf(),
    var profilePhoto: String = "",
    val genresSelected: MutableList<String> = mutableListOf(),
    var tutorial: Int = 0
)