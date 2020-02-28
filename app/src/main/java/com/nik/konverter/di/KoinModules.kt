package com.nik.konverter.di

import com.nik.konverter.ui.main.MainViewModel
import com.nik.konverter.ui.splash.SplashViewModel
import com.nik.konverter.ui.valutes.ValuteViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {
    viewModel { MainViewModel() }
}

val splashModule = module {
    viewModel { SplashViewModel() }
}

val valuteModule = module {
    viewModel { ValuteViewModel() }
}

