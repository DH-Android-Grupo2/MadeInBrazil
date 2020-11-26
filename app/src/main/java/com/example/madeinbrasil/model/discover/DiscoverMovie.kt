package com.example.madeinbrasil.model.discover

import com.example.madeinbrasil.model.discover.ResultDiscoverMovie

data class DiscoverMovie(
    val page: Int,
    val resultDiscoverMovies: List<ResultDiscoverMovie>,
    val total_pages: Int,
    val total_results: Int
)