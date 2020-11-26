package com.example.madeinbrasil.model.lists

data class CreateList(
    val list_id: Int,
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)