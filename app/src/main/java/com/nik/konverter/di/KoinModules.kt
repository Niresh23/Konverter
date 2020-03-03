package com.nik.konverter.di

import com.nik.konverter.ui.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val splashModule = module {
    viewModel { SplashViewModel() }
}