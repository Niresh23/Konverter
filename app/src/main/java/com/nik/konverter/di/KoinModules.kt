package com.nik.konverter.di

import com.nik.konverter.database.AppDatabase
import com.nik.konverter.database.ValutaDao
import com.nik.konverter.model.DatabaseRepository
import com.nik.konverter.model.Repository
import com.nik.konverter.ui.main.MainViewModel
import com.nik.konverter.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit


val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val repositoryModule = module {
    single<ValutaDao> { AppDatabase.getInstance(context = androidContext()).valutaDao() }
    single<DatabaseRepository> { DatabaseRepository.getInstance(get()) }
    single<Repository> { Repository(get()) }
}
