package com.example.madeinbrasil.api.firebase

sealed class FirebaseResponse {
    class OnSucess(val data: Any): FirebaseResponse()
    class OnFailure(val message: String): FirebaseResponse()
}