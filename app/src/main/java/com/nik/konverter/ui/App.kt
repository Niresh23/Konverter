package com.nik.konverter.ui

import android.app.Application
import com.nik.konverter.di.mainModule
import com.nik.konverter.di.splashModule
import com.nik.konverter.di.valuteModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(mainModule, splashModule, valuteModule))
    }
}