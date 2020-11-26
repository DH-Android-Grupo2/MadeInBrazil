package com.example.madeinbrasil.API

sealed class ResponseAPI {
    class Success(val data: Any?): ResponseAPI()
    class Error (val message: String): ResponseAPI()
}