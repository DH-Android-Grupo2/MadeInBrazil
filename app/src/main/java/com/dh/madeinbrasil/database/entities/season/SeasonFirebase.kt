package com.dh.madeinbrasil.database.entities.season

data class SeasonFirebase (
        val air_date: String? = "",
        val episode_count: Int? = 0,
        val id: Int = 0,
        var name: String? = "",
        var overview: String? = "",
        var posterPath: String? = "",
        val season_number: Int? = 0
)