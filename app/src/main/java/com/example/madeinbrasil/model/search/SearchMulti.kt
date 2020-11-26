package com.example.madeinbrasil.model.search

import com.example.madeinbrasil.model.search.ResultSearchMulti

data class SearchMulti(
    val page: Int,
    val results: List<ResultSearchMulti>,
    val total_pages: Int,
    val total_results: Int
)