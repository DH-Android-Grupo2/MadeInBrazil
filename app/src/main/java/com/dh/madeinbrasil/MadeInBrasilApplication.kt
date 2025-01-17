package com.dh.madeinbrasil


import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class MadeInBrasilApplication: MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}