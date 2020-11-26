package com.example.madeinbrasil.model.search

import com.example.madeinbrasil.model.search.ResultSearchMovie

data class SearchMovie(
    val page: Int,
    val resultMovies: List<ResultSearchMovie>,
    val total_pages: Int,
    val total_results: Int
)