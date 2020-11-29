package com.example.madeinbrasil.extensions

import com.example.madeinbrasil.utils.Constants.Api.BASE_URL_ORIGINAL_IMAGE

fun String.unmask(): String {
    return this.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "")
        .replace("[(]".toRegex(), "").replace(
            "[ ]".toRegex(), ""
        ).replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
}

fun String.getFullImagePath(): String {
    return "${BASE_URL_ORIGINAL_IMAGE}${this}"
}

fun String.getFirst4Chars():String{
    return "${this[0]}${this[1]}${this[2]}${this[3]}"
}