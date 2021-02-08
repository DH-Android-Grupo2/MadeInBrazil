package com.example.madeinbrasil.model.customLists

import com.example.madeinbrasil.model.customLists.firebase.CustomList
import com.example.madeinbrasil.model.customLists.firebase.Media

data class ListWithMedia(
    val list: CustomList,
    val mediaList: List<Media>
)