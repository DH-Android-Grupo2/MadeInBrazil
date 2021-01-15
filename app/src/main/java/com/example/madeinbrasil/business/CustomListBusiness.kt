package com.example.madeinbrasil.business

import android.content.Context
import com.example.madeinbrasil.model.customLists.CustomList
import com.example.madeinbrasil.model.customLists.relation.ListWithMedia
import com.example.madeinbrasil.repository.CustomListRepository

class CustomListBusiness(context: Context) {

    private val customListRepository by lazy {
        CustomListRepository(context)
    }

    suspend fun createList(list: ListWithMedia) {
        customListRepository.createList(list)
    }

    suspend fun getLists(): List<ListWithMedia> {
       return customListRepository.getLists()
    }
}