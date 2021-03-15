package com.dh.madeinbrasil.model.home

import com.dh.madeinbrasil.model.classe.Comments

class CommentRepository {

    fun setComments(): List<Comments> {
        val commentList: MutableList<Comments> = mutableListOf()

        val comment1 = Comments("João Paulo",
        "https://images.pexels.com/photos/2078265/pexels-photo-2078265.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "Ótimo filme, elenco fantástico e um show de atuação de todos. História super envolvente, um dos melhores que já vi!!"
        )

        val comment2 = Comments("Ana Claúdia",
            "https://images.pexels.com/photos/1310522/pexels-photo-1310522.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "Esse filme é um tesouro nacional. É impossível não gostar, e se não gostar, é porque não assistiu direito."
        )

        val comment3 = Comments("José Diaz",
            "https://images.pexels.com/photos/1484794/pexels-photo-1484794.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "Ótimo filme, elenco fantástico e um show de atuação de todos. História super envolvente, um dos melhores que já vi!!"
        )

        commentList.addAll(listOf(comment1, comment2, comment3))
        return commentList
    }
}