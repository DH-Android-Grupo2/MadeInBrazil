package com.example.madeinbrasil.model.watchProviders

data class BR(
    val flatrate: List<Flatrate>,
    val link: String,
    val buy: List<Buy>,
    val rent: List<Rent>
)