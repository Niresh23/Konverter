package com.nik.konverter.ui

import android.app.Application
import com.nik.konverter.di.mainModule
import com.nik.konverter.di.repositoryModule
import com.nik.konverter.di.splashModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(splashModule, mainModule, repositoryModule))
    }
}