package com.example.madeinbrasil.model.home

import com.example.madeinbrasil.model.`class`.Actors

class ActorsRepository {

    fun setActors(): List<Actors> {
        val listActors: MutableList<Actors> = mutableListOf()

        val actor1 = Actors("Sônia Braga",
            "https://br.web.img3.acsta.net/pictures/16/02/17/17/23/223334.jpg")
        val actor2 = Actors("Sônia Braga",
            "https://br.web.img3.acsta.net/pictures/16/02/17/17/23/223334.jpg")
        val actor3 = Actors("Sônia Braga",
        "https://br.web.img3.acsta.net/pictures/16/02/17/17/23/223334.jpg")
        val actor4 = Actors("Sônia Braga",
            "https://br.web.img3.acsta.net/pictures/16/02/17/17/23/223334.jpg")
        val actor5 = Actors("Sônia Braga",
            "https://br.web.img3.acsta.net/pictures/16/02/17/17/23/223334.jpg")

        listActors.addAll(listOf(actor1, actor2, actor3, actor4, actor5))

        return listActors
    }
}