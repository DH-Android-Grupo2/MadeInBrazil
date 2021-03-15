package com.dh.madeinbrasil.model.classe

data class CommentFirebase (
        val userId: String? = null,
        val userName: String? = null,
        val userImage: String? = null,
        val commentText: String? = null,
        val commentId: String? = null,
        val midiaId: Long? = null
        )