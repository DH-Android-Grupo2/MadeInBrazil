package com.dh.madeinbrasil.utils

class Constants {

    object Api {
        const val BASE_URL_v3 = "https://api.themoviedb.org/3/"
        const val BASE_URL_v4 = "https://api.themoviedb.org/4/"
        const val BASE_URL_ORIGINAL_IMAGE = "https://image.tmdb.org/t/p/original"
        const val BASE_URL_YOUTUBE_BROWSER = "https://www.youtube.com/watch?v="
        const val BASE_URL_YOUTUBE_APP = "vnd.youtube:"
        const val API_AUTH_NAME = "Authorization"
        const val API_AUTH_VALUE = "Bearer YOUR_API_KEY"
        const val API_CONTENT_TYPE_NAME = "Content-Type"
        const val API_CONTENT_TYPE_VALUE = "application/json;charset=utf-8"
        const val QUERY_PARAM_LANGUAGE_LABEL = "language"
        const val QUERY_PARAM_LANGUAGE_VALUE = "pt-BR"
        const val QUERY_PARAM_REGION_LABEL = "region"
        const val QUERY_PARAM_REGION_VALUE = "BR"
        const val QUERY_PARAM_APPEND_LABEL = "append_to_response"
        const val QUERY_PARAM_APPEND_VALUE = "videos,credits,recommendations,similar,watch/providers"
        const val QUERY_PARAM_APPEND_VALUE_PERSON = "movie_credits,tv_credits"

    }

    object ConstantsFilms {
        const val BASE_FILM_KEY = "Film"
        const val BASE_FILM_DETAILED_KEY = "MovieDetailed"
        const val BASE_MIDIA_KEY = "midiaFirebase"
        const val BASE_MEDIA_KEY = "media"
        const val BASE_SERIE_KEY = "Serie"
        const val ID_FRAGMENTS = "idFragment"
        const val BASE_ACTOR_KEY = "Actor"
        const val SEASON_KEY = "season"
        const val SEASON_KEY_OFF = "seasonId"
        const val BASE_EPISODE_KEY = "episode"
        const val VALUE = "position"
        const val SELECTED_MOVIES = "selected_movies"
        const val SELECTED_SERIES = "selected_series"
        const val TUTORIAL = "tutorial"
    }

    object Paging {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE = 1
    }

    object Firebase {
        const val DATABASE_USERS = "users"
        const val DATABASE_MIDIA = "midia"
        const val DATABASE_GENRE = "genre"
        const val DATABASE_CAST = "cast"
        const val DATABASE_LISTS = "lists"
        const val FIELD_FAVORITES = "favorites"
        const val FIELD_WATCHED = "watched"
        const val FIELD_OWNERLIST = "ownerName"
        const val DATABASE_SEASON = "season"
    }

    object CustomLists {
        const val ERROR_CREATE_LIST = "Erro ao criar a lista."
        const val ERROR_GET_LISTS = "Erro ao recuperar as listas. Tente mais tarde!"
        const val ERROR_UPDATE_LIST = "Erro ao atualizar lista."
        const val ERROR_DELETE_ITEMS = "Erro ao excluir itens."
        const val ERROR_ADD_ITEM = "Erro ao adicionar o item."
        const val ITEM_JA_ADICIONADO = "Esse item já está nessa lista!"
        const val ITEM_ADICIONADO = "Item adicionado!"
        const val DELETE_TASK_OK = "Remoção realizada!"
        const val UPDATE_TASK_OK = "Atualização realizada!"
        const val LISTS = "lists"
        const val LIST = "list"
        const val LIST_ID = "listId"
        const val CUSTOM_LIST_MEDIA_ITEM = "customListMediaItem"
        const val MOVIE = "movie"
        const val SERIE = "serie"
    }
}