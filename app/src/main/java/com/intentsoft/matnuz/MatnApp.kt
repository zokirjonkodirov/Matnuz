package com.intentsoft.matnuz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MatnApp: Application() {

    companion object {
        lateinit var app: MatnApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}