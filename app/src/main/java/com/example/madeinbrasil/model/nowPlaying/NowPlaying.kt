package com.example.madeinbrasil.model.nowPlaying

import com.example.madeinbrasil.model.upcoming.Dates
import com.example.madeinbrasil.model.upcoming.Result

data class NowPlaying(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)