package com.example.madeinbrasil.utils

class Constants {

    object Api {
        const val BASE_URL_v3 = "https://api.themoviedb.org/3/"
        const val BASE_URL_v4 = "https://api.themoviedb.org/4/"
        const val BASE_URL_ORIGINAL_IMAGE = "https://image.tmdb.org/t/p/original"
        const val API_AUTH_NAME = "Authorization"
        const val API_AUTH_VALUE = "Bearer eyJhbGciOiJIUzI1NiJ9.ey" +
                "JhdWQiOiIwMDg3YjJhMzE0ZDg0ZjQ4MTFkMWEyYzc1NTg1MDN" +
                "mMiIsInN1YiI6IjVmMWIzMmQ3NjdlMGY3MDAzNjJiN2JkMyIs" +
                "InNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kVV" +
                "hj0uZPRr6vsp8W3CxeYd8o_wRzWYNVw4lIfSs0TU"
        const val API_CONTENT_TYPE_NAME = "Content-Type"
        const val API_CONTENT_TYPE_VALUE = "application/json;charset=utf-8"
        const val QUERY_PARAM_LANGUAGE_LABEL = "language"
        const val QUERY_PARAM_LANGUAGE_VALUE = "pt-BR"
        const val QUERY_PARAM_REGION_LABEL = "region"
        const val QUERY_PARAM_REGION_VALUE = "BR"
    }

    object ConstantsFilms {
        const val BASE_FILM_KEY = "Film"
        const val BASE_SERIE_KEY = "Serie"
        const val ID_FRAGMENTS = "idFragment"
    }

    object Paging {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE = 1
    }
}