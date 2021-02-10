package com.example.madeinbrasil.model.customLists

import android.os.Parcelable
import com.example.madeinbrasil.model.customLists.firebase.CustomList
import com.example.madeinbrasil.model.customLists.firebase.Media
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListWithMedia(
    val list: CustomList,
    var mediaList: List<Media>
): Parcelable