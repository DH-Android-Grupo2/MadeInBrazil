package com.example.madeinbrasil.model.search

data class SearchSeries(
    val page: Int,
    val results: List<ResultSearchSeries>,
    val total_pages: Int,
    val total_results: Int
)