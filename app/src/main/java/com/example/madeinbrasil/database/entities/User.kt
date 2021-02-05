package com.example.madeinbrasil.database.entities

data class User (
    val email: String = "",
    val name: String = "",
    val favorites: MutableList<Int> = mutableListOf(),
    val watched: MutableList<Int> = mutableListOf(),
    val profilePhoto: String = ""
)