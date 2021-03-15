package com.dh.madeinbrasil.model.customLists

import android.os.Parcelable
import com.dh.madeinbrasil.model.customLists.firebase.CustomList
import com.dh.madeinbrasil.model.customLists.firebase.Media
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListWithMedia(
    val list: CustomList,
    var mediaList: List<Media>
): Parcelable