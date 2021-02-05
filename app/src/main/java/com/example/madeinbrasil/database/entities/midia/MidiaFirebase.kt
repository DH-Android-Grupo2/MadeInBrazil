package com.example.madeinbrasil.database.entities.midia

data class MidiaFirebase(
        var id: Int = 0,
        var backdropPath: String? = "",
        var cast: List<Int>? = listOf(),
        var episodeRunTime: String? = "",
        var firstAirDate: String? = "",
        var genres: List<Int>? = listOf(),
        var homepage: String? = "",
        var name: String? = "",
        var overview: String? = "",
        var posterPath: String? = "",
        var recommendations: List<Int>? = listOf(),
        var seasons: List<Int>? = listOf(),
        var similar: List<Int>? = listOf(),
        var voteAverage: Double? = 0.0,
)